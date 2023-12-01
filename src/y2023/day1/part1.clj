(ns y2023.day1.part1
  (:require [clojure.string :as s]))

(defn- get-digits [line]
  (->> line
       (filter (fn [c] (Character/isDigit c)))))

(let [lines (s/split-lines (slurp "src/y2023/day1/input.txt"))]
  (->> lines
       (map get-digits)
       (map (fn [digits] (vector (first digits) (last digits))))
       (map (fn [[f l]] (Integer/parseInt (str f l))))
       (reduce +)
       (println)))
