(ns y2022.day5.parse
  (:require [utils.core :refer [split* transpose]]))

(defn parse-stacks [stacks-str]
  (->> stacks-str
       (split* #"\n")
       (transpose)
       (filter (partial some #(Character/isLetterOrDigit %)))
       (map (partial filter (partial not= \space)))
       (map (fn [col] (vector (Integer/parseInt (str (last col)))
                              (drop-last col))))
       (into (sorted-map))))

(defn parse-procedure [procedure-str]
  (->> procedure-str
       (split* #"\n")
       (map (partial re-seq #"\d+"))
       (map (fn [digit-seq] (map #(Integer/parseInt %) digit-seq)))
       (map (fn [[n from to]] {:n n :from from :to to}))))
