(ns css-selector
  (:require [clojure.xml :as xml]))


(defn tag? [tag-name xml-tag]
  (let [tag-name-keyword (keyword tag-name)]
    (= tag-name-keyword (:tag xml-tag))))

(defn has-class? [class-name xml-tag]
  (= class-name (:class (:attrs xml-tag))))

(defn matcher [selector-string]
  (let [with-class-re-matcher (re-matcher #"^(\w+)\.(\w+)$" selector-string)]
    (if (.matches with-class-re-matcher)
                                        ; "tagname.classname" form
      (let [[dummy tag-name class-name] (re-groups with-class-re-matcher)]
          (fn [xml-tag]
            (and
             (tag? tag-name xml-tag)
             (has-class? class-name xml-tag))))
                                        ; "tagname" form
      (fn [xml-tag]
        (tag? selector-string xml-tag)))))

(defn query [selector xml-input]
  (filter (matcher selector)
          (xml-seq (xml/parse xml-input))))
