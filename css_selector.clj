(ns css-selector
  (:require [clojure.xml :as xml]))


(defn matcher [selector-string]
  (let [selector (keyword selector-string)]
       (fn [xml-tag]
         (= selector (:tag xml-tag)))))

(defn query [selector xml-input]
  (filter (matcher selector)
          (xml-seq (xml/parse xml-input))))
