(ns y2023.day1.part2
  (:require [clojure.string :as s]))

;; Alternatives:
;; - https://github.com/ke-hermann/advent-of-code/blob/main/clojure-solutions/src/year-2023/day1.clj
;; - https://gist.github.com/YannickFricke/614580c48bc949305c68fe6d6a3f3242

(def num-words ["one" "two" "three" "four" "five" "six" "seven" "eight" "nine"])
(def num-figures ["1" "2" "3" "4" "5" "6" "7" "8" "9"])
(def re-num-words (->> num-words
                       (concat num-figures)
                       (s/join "|")
                       ((fn [r] (str "(?=(" r "))")))
                       (re-pattern)))

(defn- get-digits [line]
  (->> (re-seq re-num-words line)
       (map (comp first (partial filter seq)))
       (map (fn [match] (or (and (some #{match} num-words)
                                 (inc (.indexOf num-words match)))
                            (Integer/parseInt match))))))

(let [lines (s/split-lines (slurp "src/y2023/day1/input.txt"))]
  (->> lines
       (map get-digits)
       (map (fn [digits] (vector (first digits) (last digits))))
       (map (fn [[f l]] (Integer/parseInt (str f l))))
       (reduce +)
       (println)))
