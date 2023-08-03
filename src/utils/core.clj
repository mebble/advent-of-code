(ns utils.core
  (:require [clojure.string :as s]))

(defn- flip
  "https://stackoverflow.com/a/44995482/5811761"
  [f]
  (fn [& xs] (apply f (reverse xs))))

(def split* 
  (flip s/split))

(defn transpose
  "https://stackoverflow.com/a/10347404/5811761"
  [m]
  (apply mapv vector m))

(defn vec-remove
  "remove elem in coll https://stackoverflow.com/a/18319708/5811761"
  [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))
