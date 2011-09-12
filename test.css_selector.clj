(ns test.css-selector
  (:use [clojure.test :only [deftest is testing run-tests]])
  (:require css-selector))

(defn input-stream [string] (java.io.ByteArrayInputStream. (.getBytes string)))

(deftest tag-name
  (is (= 1 (count (css-selector/query "name" (input-stream "<name/>"))))))

(run-tests 'test.css-selector)
