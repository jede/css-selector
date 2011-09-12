(ns css-selector
  (:require [clojure.xml :as xml]))


(defn matcher [selector-string]
  (let [with-class-re-matcher (re-matcher #"^(\w+)\.(\w+)$" selector-string)]
    (if (.matches with-class-re-matcher)
                                        ; "tagname.classname" form
      (let [[dummy tag-name class-name] (re-groups with-class-re-matcher)]
        (let [tag-name-keyword (keyword tag-name)]
          (fn [xml-tag]
            (and
             (= tag-name-keyword (:tag xml-tag))
             (= class-name (:class (:attrs xml-tag)))))))
                                        ; "tagname" form
      (let [selector (keyword selector-string)]
        (fn [xml-tag]
          (= selector (:tag xml-tag)))))))

(defn query [selector xml-input]
  (filter (matcher selector)
          (xml-seq (xml/parse xml-input))))
