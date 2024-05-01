<h1 align="center"style="font-weight:normal;">Welcome to <strong>Electronic Circuit Simulator </strong>🔋👋</h1>

![License](https://img.shields.io/github/license/barsukon/electronic-circuit-simulator)

## Table of contents
1. [Technologies](#technologies)
2. [Getting Started](#getting-started)
    1. [Pre-reqs](#pre-reqs)
    2. [Building and Running](#run)
    3. [Embedding](#embedding)
3. [Available Scripts](#scripts)
4. [Contributing](#contributing)
5. [Code of Conduct](#code-of-conduct)
6. [License](#license)

## Technologies <a name="technologies"></a>

[![Made with: Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Made with: GWT](https://img.shields.io/badge/GWT-%23FF0000.svg?style=for-the-badge&logo=GWT&logoColor=white)](https://www.gwtproject.org/)
[![Made with: HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)](https://www.w3schools.com/html/)
[![Made with: CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)](https://developer.mozilla.org/en-US/docs/Web/CSS)
[![Made with: Nginx](https://img.shields.io/badge/nginx-009900?style=for-the-badge&logo=nginx&logoColor=white)](https://nginx.org/)

## Getting Started <a name="getting-started"></a>

**ECS** is an electronic circuit simulator that runs in the browser.

You can try the hosted version of the application:

- https://ecs.maxbarsukov.ru/

### Pre-reqs <a name="pre-reqs"></a>

Make sure you have [git](https://git-scm.com/) installed.

To build and run this app locally you will need a few things:

- Use [Unix-like OS](https://www.quora.com/Why-do-people-hate-Windows);
- Install [JDK](https://openjdk.org/) **version 8** *(tested with **OpenJDK 8**)*;
- Install [Ant](https://ant.apache.org/);
- Install [Nginx](https://nginx.org/ru/download.html) *(tested with **1.20.2**)*;


Clone this repository:

    git clone git@github.com:barsukon/electronic-circuit-simulator.git

Setup dependencies:

    ./bin/dev setup


### Building and Running <a name="run"></a>

The web application can be compiled and run locally with:

```bash
./bin/dev setup
./bin/dev compile # This will put the artifacts in to the "war" directory
./bin/dev start
```

Just for reference the files you'll deploy should look like this:

```
-+ Directory containing the front page (eg "ecs")
  +- ecs.html - full page version of application
  +- iframe.html - see notes above
  ++ ecs (directory)
   +- various files built by GWT
   +- circuits (directory, containing example circuits)
   +- setuplist.txt (index in to example circuit directory)
```

### Embedding <a name="embedding"></a>

You can link to the full page version of the application using the link shown above.

If you want to embed the application in another page then use an iframe with the src being the full-page version.

You can add query parameters to link to change the applications startup behaviour. The following are supported:

```
.../ecs.html?cct=<string> // Load the circuit from the URL (like the # in the Java version)
.../ecs.html?ctz=<string> // Load the circuit from compressed data in the URL
.../ecs.html?startCircuit=<filename> // Loads the circuit named "filename" from the "Circuits" directory
.../ecs.html?running=<true|false> // Start the app without the simulation running, default true
.../ecs.html?hideSidebar=<true|false> // Hide the sidebar, default false
.../ecs.html?hideMenu=<true|false> // Hide the menu, default false
.../ecs.html?editable=<true|false> // Allow circuit editing, default true
```

## Available Scripts <a name="scripts"></a>

- `./bin/dev setup` – download **JDK**, **Ant** and **GWT**, generate `build.xml`;
- `./bin/dev compile` – compile project;
- `./bin/dev package` – make package from your `war`;
- `./bin/dev codeserver` – run **GWT** code server;
- `./bin/dev webserver` – run Python's `http.server` to host your app;
- `./bin/dev start` – run both of `codeserver` and `webserver`.

## Contributing <a name="contributing"></a>

Bug reports and pull requests are welcome on GitHub at https://github.com/barsukon/electronic-circuit-simulator.
This project is intended to be a safe, welcoming space for collaboration, and contributors are expected to adhere to the [code of conduct](https://github.com/barsukon/electronic-circuit-simulator/blob/master/CODE_OF_CONDUCT.md).


## Code of Conduct <a name="code-of-conduct"></a>

Everyone interacting in the **ECS** project's codebases, issue trackers, chat rooms and mailing lists is expected to follow the [code of conduct](https://github.com/barsukon/electronic-circuit-simulator/blob/master/CODE_OF_CONDUCT.md).


## License <a name="license"></a>

The application is available as open source under the terms of the [GNU General Public License version 2](https://opensource.org/license/gpl-2-0).

*Copyright 2023 barsukon team*

**Leave a star ⭐ if you find this project useful.**
