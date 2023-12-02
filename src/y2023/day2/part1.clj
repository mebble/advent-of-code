(ns y2023.day2.part1
  (:require
   [clojure.string :as s]))

(defn- keywordise [m]
  (->> m
       (map (fn [[k v]] [(keyword k) v]))
       (into {})))

(defn- parse-cube-set [set-pairs]
  (->> set-pairs
       (map (fn [pair-str] (let [[num col] (s/split pair-str #" ")]
                             (vector col (Integer/parseInt num)))))
       (into {})
       (keywordise)))

(defn- parse-game [line]
  (let [[game-name game-body] (s/split line #":")
        [_ id-str] (s/split game-name #" ")
        game-id (Integer/parseInt id-str)
        sets-str (s/split game-body #";")
        cube-sets-pairs (map (fn [s] (map s/trim (s/split s #","))) sets-str)
        cube-sets (map parse-cube-set cube-sets-pairs)]
    {:id game-id :cube-sets cube-sets}))

(def cubes-in-bag {:red 12 :green 13 :blue 14})
(defn- game-possible? [{:keys [cube-sets]}]
  (every? (fn [cube-set]
            (and (<= (:blue cube-set 0) (:blue cubes-in-bag))
                 (<= (:red cube-set 0) (:red cubes-in-bag))
                 (<= (:green cube-set 0) (:green cubes-in-bag))))
          cube-sets))

(let [lines (s/split-lines (slurp "src/y2023/day2/input.txt"))]
  (->> lines
       (map parse-game)
       (filter game-possible?)
       (map :id)
       (reduce +)
       (println)))
