(ns main
  (:require [reagent.core :as r]
            [cljs.core.async :refer (chan put! <! go go-loop timeout)]
            [breakout :as b]
            [canvas :as c]
            ))


(defn draw-cells [component state]

  (let [canvas (c/get-canvas-from-component component)
        ctx (c/get-ctx canvas)
        bricks (:bricks state)
        ball (:ball state)
        paddle (:paddle state)]
    (set! (. ctx -fillStyle) (str "white"))
    ;; draw bricks
    (doseq [ {:keys [x y width height color :as bricks ]} bricks]
      (c/rect ctx x y width height color "black")
      )
    ;; draw ball
    (c/rect ctx (:x ball) (:y ball) 5 5 "black" "black")
    ;; draw paddle
    (c/rect ctx (:x paddle) (:y paddle) (:width paddle) (:height paddle) "green" "green")
    
    )
  )
                                        ;)


(defn canvas [state]
  (let [state state]
    (r/create-class
     {:display-name "canvas"
      :component-did-mount  #(draw-cells % @state) 
      :component-did-update #(draw-cells % @state)
      :reagent-render (fn  []
                        @state
                        [:canvas
                         {:on-click (println "CLICK")
                          :on-mouseMove #(println "HEsLLO")
                          :id "c" :width 400 :height 350 :style {:border "2px solid green"}}])
      }
     )))




(def state (atom (b/create-initial-state)))
(defn main-component []
  [:div 
   [:h1 "This is the component"]
   [:hr]
   [canvas state]
   [:hr]
   ])


(defn mount [c]
  (r/render-component [c] (.getElementById js/document "app"))
  )

(defn reload! []
  (mount main-component)
  (print "Hello reload!"))

(defn main! []
  (mount main-component)
  (print "Hello Main"))
