(ns css-selector
  (:use clojure.xml))

(defn query [selector xml-input]
  (filter (fn [xml-tag] 
            (= (keyword selector) (:tag xml-tag)))
          (xml-seq (parse xml-input)) ))

