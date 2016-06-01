# Huon [![Build Status](https://travis-ci.org/harto/huon.svg?branch=master)](https://travis-ci.org/harto/huon)

A logging library for ClojureScript that wraps [`goog.debug.Logger`](https://closure-library.googlecode.com/git-history/docs/namespace_goog_log.html).


## Goals

 * Leverage GClosure functionality
 * Lazy message evaluation
 * Simple API


## Non-goals

 * Highly configurable


## Installation

Add `[org.harto/huon "0.2.1"]` as a dependency in `project.clj`.


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
 [  0.021s] [foo.bar] hello world
```

Available macros are `debug`, `info`, `warn` and `error`.


## License

MIT; see [LICENSE](./LICENSE).
