# MCP Server

## How to run locally

Install `uv`
```
curl -LsSf https://astral.sh/uv/install.sh | sh
```

Create a virtual environment:

```
uv venv
source .venv/bin/activate
```

### Install dependencies

```
uv add "mcp[cli]" httpx
```

### how to run the app

One can use the `@modelcontextprotocol/inspector` tool to run an UI inspector to 
test the server.
If using nvm:

```
nvm install 20   
nvm use 20
```

Then install `@modelcontextprotocol/inspector`
```
npm install -g @modelcontextprotocol/inspector
```

Finally, you can run it with the command:

```
npx @modelcontextprotocol/inspector uv run main.py
```

## How to build the docker image

To build the docker image, run the following command:

```
docker build -t mcp-server-reports .
```

It is *not* necessary to run the docker image (the AI agent will do it later).