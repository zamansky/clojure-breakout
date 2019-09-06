(ns  breakout
  (:require [reagent.core :as r]
            ))


(def colors ["blue" "read" "yellow" "green" "violet" "ivory" "burlywood"])

(def sample-state {:paddle {}
                   :speed 100
                   :bricks {}
                   :width 300
                   :height 300
                   :brickstart 50
                   :rows 5
                   
                   })


