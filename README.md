# Huon

A logging library for ClojureScript that wraps [`goog.debug.Logger`](https://closure-library.googlecode.com/git-history/docs/namespace_goog_log.html).


## Goals

 * Leverage GClosure functionality
 * Lazy message evaluation
 * Simple API


## Non-goals

 * Highly configurable


## Installation

Add `[org.harto/huon "0.1.0"]` as a dependency in `project.clj`.


## Usage

```cljs
(ns foo.bar
  (:require [huon.log :as log]))

;; optional; defaults to :warn
(log/set-root-level! :debug)

(log/info "hello" "world")
```

Output:
```
 [  0.021s] [foo.bar] hello world
```

Available macros are `debug`, `info`, `warn` and `error`.


## License

MIT; see [LICENSE](./LICENSE).
