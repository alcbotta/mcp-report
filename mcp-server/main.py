import sys
import os
from typing import Any, Dict, List, Optional

import httpx
from mcp.server import Server
import mcp.types as types
from mcp.server import stdio

sys.path.insert (0, os.path.dirname(os.path.abspath(__file__)))

BASE_URL = os.getenv("REPORTS_API_BASE_URL", "http://localhost:8087/api/v1/reports")
TIMEOUT_SECS = float(os.getenv("REPORTS_API_TIMEOUT", "10"))

server = Server("Reports API Server")

def _http_client() -> httpx.Client:
    return httpx.Client(timeout=TIMEOUT_SECS)

def _build_params(priority: Optional[str], title: Optional[str], description: Optional[str], page: int, size: int, sort: Optional[str]) -> Dict[str, str]:
    params: Dict[str, str] = {}
    if priority:
        params["priority"] = priority
    if title:
        params["title"] = title
    if description:
        params["description"] = description
    params["page"] = str(page)
    params["size"] = str(size)
    if sort:
        params["sort"] = sort
    return params

def _query_reports_impl(priority: Optional[str] = None, 
                        title: Optional[str] = None, description: Optional[str] = None, 
                        page: int = 0, size: int = 20, sort: Optional[str] = None) -> Dict[str, Any]:
    url = BASE_URL
    params = _build_params(priority, title, description, page, size, sort)
    with _http_client() as client:
        resp = client.get(url, params=params)
        resp.raise_for_status()
        data = resp.json()
    content = data.get("content", [])
    items = [{"id": item.get("id"), "priority": item.get("priority"), "title": item.get("title"), "description": item.get("description")} for item in content]
    page_summary = {"page": int(data.get("number", 0)), "size": int(data.get("size", len(items))), "total_elements": int(data.get("totalElements", len(items))), "total_pages": int(data.get("totalPages", 1)), "number_of_elements": int(data.get("numberOfElements", len(items))), "first": bool(data.get("first", True)), "last": bool(data.get("last", True))}
    return {"items": items, "page": page_summary, "raw": data}

_QUERY_REPORTS_SCHEMA: Dict[str, Any] = {
    "type": "object",
    "properties": {
        "priority": {"type": "string", "description": "Filter by priority (contains, case-insensitive)."},
        "title": {"type": "string", "description": "Filter by title (contains, case-insensitive)."},
        "description": {"type": "string", "description": "Filter by description (contains, case-insensitive)."},
        "page": {"type": "integer", "minimum": 0, "default": 0},
        "size": {"type": "integer", "minimum": 1, "default": 20},
        "sort": {"type": "string", "description": "Spring sort: field,asc|desc"},
    },
    "additionalProperties": False,
}

@server.list_tools()
async def list_tools() -> List[types.Tool]:
    return [types.Tool(name="query_reports", description="Query the Reports API with QueryDSL-backed filters and pagination.", inputSchema=_QUERY_REPORTS_SCHEMA)]

@server.call_tool()
async def call_tool(name: str, arguments: Any) -> List[types.TextContent]:
    if name != "query_reports":
        raise ValueError (f"Unknown tool: {name}")

    args = arguments or {}
    result = _query_reports_impl(priority=args.get("priority"), title=args.get("title"), 
                                 description=args.get("description"), page=int(args.get("page", 0)), 
                                 size=int(args.get("size", 20)), sort=args.get("sort"))
    return [types.TextContent (type="text", text=str (result))]


@server.list_resources()
async def list_resources() -> List[types.Resource]:
    return [types.Resource(name="Reports API Usage", uri="reports://usage", mimeType="text/markdown", description="Usage instructions for Reports API MCP server")]

@server.read_resource()
async def read_resource(uri: str) -> str:
    uri_str = str (uri)
    if uri_str == "reports://usage":
        try:
            with open (os.path.join (os.path.dirname(__file__), "resources", "ReportsApiUsage.md"), "r") as f:
                return f.read()
        except Exception as e:
            raise ValueError (f"Error reading ReportsApiUsage: {str (e)}")
    raise ValueError (f"Unknown resource: {uri}")

if __name__ == "__main__":
    import asyncio
    async def main() -> None:
        async with stdio.stdio_server() as (read_stream, write_stream):
            await server.run(read_stream, write_stream, server.create_initialization_options())
    asyncio.run(main())
