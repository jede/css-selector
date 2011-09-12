(ns test.css-selector
  (:use [clojure.test :only [deftest is]])
  (:require css-selector))

(defn input-stream [string] (java.io.ByteArrayInputStream. (.getBytes string "utf-8")))

(deftest tag-name-only
  (is (= 1 (count (css-selector/query "name" (input-stream "<name/>")))))
  (is (= 0 (count (css-selector/query "name" (input-stream "<noname/>")))))
  (is (= 2 (count (css-selector/query "name" (input-stream "<stuff><name/><name/></stuff>"))))))

;(deftest tag-name-with-class
;  (is (= 1 (count (css-selector/query "name.red" (input-stream "<stuff><name class=\"blue\"/><name class=\"red\"/></stuff>"))))))

(clojure.test/run-tests 'test.css-selector)
