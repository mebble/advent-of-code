(ns y2024.day7.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [perm-with-repetition]]))

(defn- parse-input [input]
  (->> (s/split-lines input)
       (map (fn [row] (s/split row #": ")))
       (map (fn [[test-s nums-s]] [(read-string test-s)
                                   (map read-string (s/split nums-s #" "))]))))

(defn- operate [nums ops]
  (loop [[a b & others] nums
         ops ops]
    (let [op (first ops)
          other-ops (rest ops)]
      (if (nil? others)
        (op a b)
        (recur (cons (op a b) others)
               other-ops)))))

(defn- valid-equation? [ops equation]
  (let [[test-num nums] equation]
    (->> (count nums)
         (dec)
         (perm-with-repetition ops)
         (map (fn [op-combo] (operate nums op-combo)))
         (some (fn [total] (= total test-num))))))

;; Part 1
(let [equations (parse-input (slurp "src/y2024/day7/input.txt"))]
  (->> equations
       (filter (partial valid-equation? [+ *]))
       (map first)
       (apply +)))

(defn- || [x y] (read-string (str x y)))

;; Part 2
(let [equations (parse-input (slurp "src/y2024/day7/input.txt"))]
  (->> equations
       (filter (partial valid-equation? [+ * ||]))
       (map first)
       (apply +)))
