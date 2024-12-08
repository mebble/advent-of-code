(ns y2024.common
  (:require [clojure.math.combinatorics :as combo]))

(defn parse-int [s]
  (Integer/parseInt s))

(defn remove-at [i items]
  (into (subvec items 0 i) (subvec items (inc i))))

(defn replace-at [s i c]
  (vec (concat (subvec s 0 i)
               [c]
               (subvec s (inc i)))))

(defn perm-with-repetition [items num-slots]
  (apply combo/cartesian-product (repeat num-slots items)))
