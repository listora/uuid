(ns uuid.core
  "Provides some useful functions for working with UUIDs, built on top of
  `java.util.UUID`.

  Targeted at type-4 UUIDs. For more information you can consult [Wikipedia][],
  and the [Java docs][].

  [Wikipedia]: http://en.wikipedia.org/wiki/Universally_unique_identifier
  [Java docs]: http://docs.oracle.com/javase/6/docs/api/java/util/UUID.html")

(def uuid-pattern
  "A regular expression that will match type-4 UUIDs. This is a strict pattern,
  which is less forgiving than `java.util.UUID/fromString`."
  #"[0-9a-fA-F]{8}(?:-[0-9a-fA-F]{4}){3}-[0-9a-fA-F]{12}")

(defn from-string
  "Builds a java.util.UUID from the given string."
  [^String s]
  (java.util.UUID/fromString s))

(defn extract-uuids
  "Extracts any and all UUIDs in the given string into a lazy sequence."
  [^String s]
  (map from-string (re-seq uuid-pattern s)))

(defn matches-uuid?
  "Checks if the given string contains exactly one UUID, and nothing more."
  [^String s]
  (re-matches uuid-pattern s))

(defn contains-uuid?
  "Checks if the given string contains a UUID, using regex."
  [^String s]
  (not-empty (extract-uuids s)))

(defn parse-string
  "Parses a string looking for one UUID via `from-string`.

  Can be executed in a strict, or non-strict mode. When strict an exception
  will be produced if the string does not contain a type-4 UUID.

  Takes an optional second argument, which when truthy, causes `parse-string`
  to throw an `IllegalArgumentException` if the string does not match
  `uuid-pattern`.

  Defaults to non-strict mode, and returns nil when the string does not match
  uuid-pattern."
  ([^String s]
   (parse-string s false))
  ([^String s strict?]
   (if strict?
     (from-string s)
     (try
       (from-string s)
       (catch IllegalArgumentException _ nil)))))

(defn rand-uuid
  "Returns a random java.util.UUID."
  []
  (java.util.UUID/randomUUID))

(defmulti uuid
  "Defines transformations to UUIDs.

  Supports conversion of a string to UUID, and a UUID to itself. Implementation
  of other conversions can be implemented with defmulti, with dispatch on
  type."
  class)

(defmethod uuid String [x] (parse-string x))
(defmethod uuid java.util.UUID [x] x)
(defmethod uuid nil [_] nil)
