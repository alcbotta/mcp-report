# Reports API MCP Server Usage


## Tool: `query_reports`
Input JSON (all fields optional unless specified):
```
{{ "priority": "high|medium|low", "title": "outage", "description": null, "page": 0, "size": 10, "sort": "title,desc"}}
```

### Examples
- Title contains "outage":
  ```
  query_reports {{"title": "outage"}}
  ```
- Priority filter + sort:
  ```
  query_reports {{"priority": "high", "sort": "title,desc"}}
  ```
- Pagination (page index starts at 0):
  ```
  query_reports {{"page": 1, "size": 5}}
  ```

**Notes**
- Your Spring Data QuerydslBinder maps all `String` fields to `containsIgnoreCase`.
- Sorting uses Spring Data syntax: `sort=field,asc|desc`.
- The tool returns `items`, `page`, and `raw` (full Spring page object).