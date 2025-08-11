

## How to build and run the docker image


To build the image, run the following command:

```shell
docker build -t mcpreport:latest .
```

and to run it, then execute:
```shell
docker run -p 8087:8087 --name mcpreport mcpreport:latest
```