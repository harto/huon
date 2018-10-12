# Huon [![Build Status](https://travis-ci.org/harto/huon.svg?branch=master)](https://travis-ci.org/harto/huon)

A logging library for ClojureScript that wraps [`goog.debug.Logger`](https://google.github.io/closure-library/api/goog.debug.Logger.html).


## Goals

 * Leverage GClosure functionality
 * Lazy message evaluation
 * Simple API


## Non-goals

 * Highly configurable


## Installation

Add `[org.harto/huon "0.5.0"]` as a dependency in `project.clj`.


## Usage

```cljs
(ns foo.bar
  (:require [huon.log :as log]))

;; required; once per app
(log/enable!)

;; optional; defaults to :warn
(log/set-root-level! :info)

(log/debug "an invisible message")
(log/info "hello" "world")
```

Output:
```
 [  0.021s] [foo.bar:11] [INFO] hello world
```

Available macros are `debug`, `info`, `warn` and `error`.


## Development

The automated test suite is runnable via `bin/run-tests`.


## License

MIT; see [LICENSE](./LICENSE).
