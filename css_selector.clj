(ns css-selector
  (:require [clojure.xml :as xml]
            [clojure.string :as string]))


(defn tag? [tag-name xml-tag]
  (let [tag-name-keyword (keyword tag-name)]
    (= tag-name-keyword (:tag xml-tag))))

(defn has-class? [class-name xml-tag]
  (= class-name (:class (:attrs xml-tag))))

(defn matcher [selector-string]
  (let [[whole-match tag-name class-name] (re-find #"^(\w+)?(?:\.(\w+))?$" selector-string)]
    (if (not whole-match)
      (fn [xml-tag] false)
      (let [true-fn
            (fn [xml-tag] true)
            tag-fn
            (if tag-name
              (fn [xml-tag] (tag? tag-name xml-tag))
              true-fn)
            class-fn
            (if class-name
              (fn [xml-tag] (has-class? class-name xml-tag))
              true-fn)]
        (fn [xml-tag]
          (and (tag-fn xml-tag) (class-fn xml-tag)))))))
  

(defn query-inner [selector-parts flattened-xml]
  (if (empty? selector-parts)
    flattened-xml
    (query-inner (rest selector-parts)
                 (filter (matcher (first selector-parts)) flattened-xml))))

(defn query [selector xml-input]
  (if (string/blank? selector)
    ()
    (let [flattened-xml (xml-seq (xml/parse xml-input))
          selector-parts (string/split selector #"\s+")]
      (query-inner selector-parts flattened-xml))))

