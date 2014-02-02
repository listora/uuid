# uuid

A Clojure library designed to ease working with UUIDs that have been
converted to strings, perhaps by a third-party in JSON.

## Installation

![Leiningen version](https://clojars.org/listora/uuid/latest-version.svg)

## Usage

``` clj
(use 'uuid.core)

;; Are we dealing with a UUID here?
(matches-uuid? "2e4c0e69-fb52-454c-9ac6-2551a0696aba")
; => true

;; Do we have any UUIDs here?
(contains-uuid? "Where did I put that 2e4c0e69-fb52-454c-9ac6-2551a0696aba?")
; => true

;; Find all UUIDS
(extract-uuids "Where did I put that 710028c5-3307-4394-8c8d-3a7cef36d00a?")
; => (#uuid "710028c5-3307-4394-8c8d-3a7cef36d00a")

;; Parsing a string
(parse-string "98ca6ebd-d1be-4630-93ca-9401fc7562ed") ; => java.util.UUID
(parse-string "oh-no") ; => nil
(parse-string "oh-no" :strict) ; => IllegalArgumentException

;; To parse any old thing, with strings and UUIDs supported
;; out-of-the-box:
(uuid "6be293fc-4ed5-4ad9-9ecd-674d6c2576e0")
(uuid #uuid "6be293fc-4ed5-4ad9-9ecd-674d6c2576e0")
```

## License

Copyright © 2014 Listora

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
