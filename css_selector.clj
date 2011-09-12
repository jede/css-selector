(ns css-selector
  (:require [clojure.xml :as xml]))


(defn tag? [tag-name xml-tag]
  (let [tag-name-keyword (keyword tag-name)]
    (= tag-name-keyword (:tag xml-tag))))

(defn has-class? [class-name xml-tag]
  (= class-name (:class (:attrs xml-tag))))

(defn matcher [selector-string]
  (let [[dummy tag-name class-name] (re-find #"^(\w+)?(?:\.(\w+))?$" selector-string)]
    (let [tag-fn
          (if tag-name
            (fn [xml-tag] (tag? tag-name xml-tag))
            (fn [xml-tag] true))
          class-fn
          (if class-name
            (fn [xml-tag] (has-class? class-name xml-tag))
            (fn [xml-tag] true))]
      (fn [xml-tag]
        (and (tag-fn xml-tag) (class-fn xml-tag))))))


(defn query [selector xml-input]
  (filter (matcher selector)
          (xml-seq (xml/parse xml-input))))
