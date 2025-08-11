# MCP Client

## Requirements

### MCPHost

Install the latest version of [go](https://go.dev/doc/install)

Then, install `mcphost` 

```
go install github.com/mark3labs/mcphost@latest

```

### Ollama

Install Ollama from https://ollama.ai
Pull qwen3 model:

```
ollama pull qwen3
```

## Running the AI Agent

```
mcphost -m ollama:qwen3 --config ./mcp-servers.config.json
```

Sample questions:

- how many reports are of high priority and what is their title?
- how many reports mention an outage in their title? Show me their description. Limit the search to 20.