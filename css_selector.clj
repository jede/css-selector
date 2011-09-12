(ns css-selector
  (:require [clojure.xml :as xml]))

(defn query [selector xml-input]
  (filter (fn [xml-tag] 
            (= (keyword selector) (:tag xml-tag)))
          (xml-seq (xml/parse xml-input))))
