(ns y2024.day5.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [parse-int]]))

(defn- parse-rules [rules-str]
  (s/split-lines rules-str))

(defn- parse-updates [updates-str]
  (->> updates-str
       (s/split-lines)
       (map (fn [u] (s/split u #",")))
       (map (fn [u] (map parse-int u)))))

(defn- parse-input [input]
  (let [[rules-str updates-str] (s/split input #"\n\n")]
    [(set (parse-rules rules-str))
     (parse-updates updates-str)]))

(defn- satisfies-rules? [rules p1 p2]
  (let [front (str p1 "|" p2)
        back (str p2 "|" p1)]
    (cond
      (contains? rules front) true
      (contains? rules back) false
      :else true)))

(defn- correct? [rules update]
  (loop [pages update]
    (if (empty? pages)
      true
      (let [page (first pages)
            others (rest pages)
            good? (every? (fn [p] (satisfies-rules? rules page p)) others)]
        (if-not good?
          false
          (recur (rest pages)))))))

(defn- middle-page [update]
  (nth update (quot (count update) 2)))

;; Part 1
(let [[rules updates] (parse-input (slurp "src/y2024/day5/input.txt"))]
  (->> updates
       (filter (partial correct? rules))
       (map middle-page)
       (apply +)))

(defn- fix-update [rules update]
  (sort (fn [p1 p2]
          (if (satisfies-rules? rules p1 p2)
            -1
            1))
        update))

;; Part 2
(let [[rules updates] (parse-input (slurp "src/y2024/day5/input.txt"))]
  (->> updates
       (filter (complement (partial correct? rules)))
       (map (partial fix-update rules))
       (map middle-page)
       (apply +)))
