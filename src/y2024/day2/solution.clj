(ns y2024.day2.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [parse-int remove-at]]))

(defn- parse-input [input]
  (let [lines (s/split-lines input)]
    (->> lines
         (map (fn [line] (s/split line #" ")))
         (map (fn [xs] (map parse-int xs))))))

(defn- small-gap [_ctx l1 l2]
  (let [diff (abs (- l1 l2))]
    (and (>= diff 1)
         (<= diff 3))))

(defn- one-direction [ctx l1 l2]
  (case (:inc? ctx)
    true (> l2 l1)
    false (< l2 l1)
    true))

(defn- set-inc [ctx l1 l2]
  (let [inc? (:inc? ctx)]
    (if (nil? inc?)
      (> l2 l1)
      inc?)))

(defn- safe-pairs [ctx [l1 l2]]
  {:inc? (set-inc ctx l1 l2)
   :safe? (and (small-gap ctx l1 l2)
               (one-direction ctx l1 l2))})

(defn- safe-report? [report]
  (->> (rest report)
       (map vector report)
       (reductions safe-pairs {:safe? true})
       (every? :safe?)))

;; Part 1
(let [reports (parse-input (slurp "src/y2024/day2/input.txt"))]
  (->> reports
       (filter safe-report?)
       (count)))

(defn- sub-reports [report]
    (->> (range (count report))
         (map (fn [i] (remove-at i (vec report))))))

(defn- safe-sub-reports? [report]
  (some safe-report? (sub-reports report)))

(defn- safe-report-dampened? [report]
  (or (safe-report? report)
      (safe-sub-reports? report)))

;; Part 2
(let [reports (parse-input (slurp "src/y2024/day2/input.txt"))]
  (->> reports
       (filter safe-report-dampened?)
       (count)))

