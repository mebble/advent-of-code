(ns y2024.day3.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [parse-int]]))

(def re-extract-do #"do\(\)")
(def re-extract-dont #"don't\(\)")
(def re-extract-mul  #"mul\(\d+,\d+\)")
(def re-parse-mul #"mul\((\d+),(\d+)\)")

(defn- extract-muls [input]
  (re-seq re-extract-mul input))

(defn- parse-mul [mul]
  (when-let [matches (re-matches re-parse-mul mul)]
    (let [[_ x1 x2] matches]
      [(parse-int x1)
       (parse-int x2)])))

(defn- multiply [[x1 x2]]
  (* x1 x2))

(defn- apply-muls [muls]
  (->> muls
       (map parse-mul)
       (filter (complement nil?))
       (map multiply)
       (apply +)))

;; Part 1
(let [muls (extract-muls (slurp "src/y2024/day3/input.txt"))]
  (apply-muls muls))

(defn- extract-muls-conditions [input]
  (let [pattern (re-pattern (s/join "|" [re-extract-do re-extract-dont re-extract-mul]))]
    (re-seq pattern input)))

(defn- get-enabled-muls [muls-n-conds]
  (loop [items muls-n-conds
         enabled-muls '()
         do? true]
    (if (empty? items)
      enabled-muls
      (let [item (first items)
            others (rest items)]
        (case item
          "do()"    (recur others enabled-muls true)
          "don't()" (recur others enabled-muls false)
          (recur others
                 (if do? (cons item enabled-muls) enabled-muls)
                 do?))))))

;; Part 2
(let [muls-n-conds (extract-muls-conditions (slurp "src/y2024/day3/input.txt"))]
  (->> muls-n-conds
       (get-enabled-muls)
       (apply-muls)))
