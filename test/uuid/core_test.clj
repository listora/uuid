(ns uuid.core-test
  (:require [clojure.test :refer :all]
            [uuid.core :refer :all]))

(def uuid-string "0d6bba43-d13c-48fb-b5c1-cad3b4b0c768")

(deftest test-extract-uuids
  (let [u (from-string uuid-string)]
    (testing "with no UUIDs"
      (is (= (extract-uuids "") [])))

    (testing "with one UUID"
      (is (= (extract-uuids uuid-string) [u])))

    (testing "with two UUIDs"
      (is (= (extract-uuids (str uuid-string "in between " uuid-string))
             [u u])))))

(deftest test-matches-uuid?
  (is (matches-uuid? uuid-string))
  (is (not (matches-uuid? (str "a" uuid-string))))
  (is (not (matches-uuid? (str "a " uuid-string))))
  (is (not (matches-uuid? "a"))))

(deftest test-contains-uuid?
  (is (contains-uuid? uuid-string))
  (is (contains-uuid? (str "a" uuid-string)))
  (is (contains-uuid? (str "a " uuid-string)))
  (is (not (contains-uuid? "0-0-0-0-0"))))

(deftest test-from-string
  (is (instance? java.util.UUID (from-string "0-0-0-0-0")))
  (is (instance? java.util.UUID (from-string uuid-string))))

(deftest test-parse-string
  (testing "without specifying strict"
    (is (nil? (parse-string "a"))))

  (testing "with strict false"
    (is (nil? (parse-string "a" false))))

  (testing "with strict truthy"
    (is (thrown? IllegalArgumentException (parse-string "a" :strict)))))

(deftest test-uuid
  (testing "with a UUID-like string"
    (let [x (uuid uuid-string)]
      (is (instance? java.util.UUID x))
      (is (= (str x) uuid-string))))

  (testing "with nil"
    (is (nil? (uuid nil))))

  (testing "with a UUID"
    (let [x (uuid (java.util.UUID/fromString uuid-string))]
      (is (instance? java.util.UUID x))
      (is (= (str x) uuid-string)))))
