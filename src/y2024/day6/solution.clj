(ns y2024.day6.solution
  (:require [clojure.string :as s]
            [y2024.common :refer [replace-at]]))

(def possible-dirs [:r :d :l :u])
(def next-dirs (cycle possible-dirs))

(defn- front [guard]
  (let [dir (:dir guard)
        [y x] (:pos guard)]
    (case dir
      :u [(dec y) x]
      :r [y (inc x)]
      :d [(inc y) x]
      [y (dec x)])))

(defn- parse-input [input]
  (let [grid (vec (map (comp vec seq) (s/split-lines input)))
        width (count (first grid))
        offset (s/index-of (s/replace input #"\n" "") "^")
        y (quot offset width)
        offset-y (* y width)
        x (- offset offset-y)]
    [grid
     {:pos [y x] :dir :u}]))

(defn- walk [grid guard]
  (loop [grid grid
         guard guard
         positions 1
         dirs next-dirs]
    (let [[y x] (:pos guard)
          next-pos (front guard)
          object (get-in grid next-pos)]
      (cond
        (nil? object) positions
        (= \# object) (recur grid
                             (assoc guard :dir (first dirs))
                             positions
                             (rest dirs))
        (= \X object) (recur (update grid y replace-at x \X)
                             (assoc guard :pos next-pos)
                             positions
                             dirs)
        :else         (recur (update grid y replace-at x \X)
                             (assoc guard :pos next-pos)
                             (inc positions)
                             dirs)))))

;; Part 1
(let [[grid guard] (parse-input (slurp "src/y2024/day6/input.txt"))]
  (walk grid guard))

;; Part 2
(do
  (defn- walk-check
    "To be called multiple times, with a grid having X's for where the guard has been in the previous calls. Carries a set of possible obstacle positions found during previous calls; positions that would cause the guard to loop"
    [grid guard looping-posts next-dirs]
    (loop [grid grid
           guard guard
           next-dirs next-dirs
           turned? false]
      (let [pos (:pos guard)
            curr-object (get-in grid pos)
            dir (:dir guard)
            turn-dir (first next-dirs)
            turned-guard (assoc guard :dir turn-dir)
            turn-pos (front turned-guard)
            turn-object (get-in grid turn-pos)
            next-pos (front guard)
            next-object (get-in grid next-pos)]
        (prn guard)
        ; (prn next-pos)
        (cond
          (nil? next-object)                           [grid guard looping-posts next-dirs]
          (= \# next-object)                           (recur grid
                                                              turned-guard
                                                              (rest next-dirs)
                                                              true)
          (and (not turned?)
               (= turn-object turn-dir)
               (not (some #{next-pos} looping-posts))) [grid
                                                        (assoc guard :pos next-pos)
                                                        (cons next-pos looping-posts)
                                                        next-dirs]
          :else                                        (recur (if (contains? (set possible-dirs)
                                                                             curr-object)
                                                                grid
                                                                (assoc-in grid pos dir))
                                                              (assoc guard :pos next-pos)
                                                              next-dirs
                                                              false)))))
  (let [[grid guard] (parse-input (slurp "src/y2024/day6/sample.txt"))]
    (let [[grid guard looping-posts] (apply walk-check (apply walk-check (walk-check grid guard '() next-dirs)))]
      [grid guard looping-posts])))
