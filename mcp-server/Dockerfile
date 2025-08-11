# syntax=docker/dockerfile:1.6
FROM ghcr.io/astral-sh/uv:python3.12-bookworm

WORKDIR /app

# Quiet + sensible defaults
ENV UV_NO_PROGRESS=1 \
    PYTHONUNBUFFERED=1 \
    PIP_DISABLE_PIP_VERSION_CHECK=1 \
    # Change this at runtime if your API isn’t in the container
    REPORTS_API_BASE_URL=http://host.docker.internal:8087/api/v1/reports

# Copy metadata first to cache deps
COPY pyproject.toml uv.lock* ./
RUN uv sync --frozen --no-dev

# Now app code (main.py + resources/)
COPY . .

# Non-root user
RUN useradd -u 10001 -m appuser && chown -R appuser:appuser /app
USER appuser

# stdio-based MCP server (no ports)
ENTRYPOINT ["uv", "run", "python", "main.py"]
