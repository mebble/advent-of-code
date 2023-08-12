(ns y2022.day7.parse
  (:require [clojure.string :as s]
            [utils.core :refer [split-space]]))

(defn- parse-command [string]
  (let [[cmd arg] (rest (split-space string))]
    {:type :cmd :cmd cmd :arg arg}))

(defn- parse-file [string]
  (let [[size filename] (split-space string)]
    {:type :file :name filename :size (Integer/parseInt size)}))

(defn- parse-directory [string]
  (let [dirname (second (split-space string))]
    {:type :dir :name dirname}))

(defn parse-line [string]
  (cond 
    (s/starts-with? string "$")   (parse-command string)
    (s/starts-with? string "dir") (parse-directory string)
    :else                         (parse-file string)))

(defn- change-dir [cwd command]
  (let [{:keys [_ arg]} command]
    (case arg
      "/"  ["/"]
      ".." (pop cwd)
      (conj cwd arg))))

(defn build-filesystem
  ([parsed-lines] (build-filesystem {"/" {}} ["/"] parsed-lines))
  ([filesystem cwd parsed-lines]
   (if (empty? parsed-lines)
     filesystem
     (let [current (first parsed-lines)
           others (rest parsed-lines)]
       (case (:type current)
         :file (recur (update-in filesystem cwd assoc (:name current) current) cwd others)
         :dir  (recur (update-in filesystem cwd update (:name current) merge {}) cwd others)
         :cmd  (case (:cmd current)
                 "cd" (recur filesystem (change-dir cwd current) others)
                 "ls" (recur filesystem cwd others)))))))
