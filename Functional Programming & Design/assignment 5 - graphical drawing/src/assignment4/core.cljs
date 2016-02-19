(ns assignment4.core
  (:require [reagent.core :as r :refer [atom]]
            [schema.core :as s :include-macros true]))


;; define schema

(def point-schema {:x s/Num :y s/Num})

(def line-schema {:start-point point-schema :end-point point-schema})

(def circle-schema {:start-point point-schema :r s/Num})

(def rectangle-schema {:start-point point-schema :width s/Num :height s/Num})

;; big ratom to hold application data and state

(defonce app-state (r/atom {:start-point {}
                            :end-point {}
                            :shape s/Keyword
                            :clicked? false
                            :shape-list    '()
                            :current-shape '()}))

;; Cursors - Handle the big ratom

(def start-point (r/cursor app-state [:start-point]))
(def end-point (r/cursor app-state [:end-point]))
(def shape (r/cursor app-state [:shape]))
(def clicked? (r/cursor app-state [:clicked?]))
(def shape-list (r/cursor app-state [:shape-list]))
(def current-shape (r/cursor app-state [:current-shape]))

;; Cursor setters and schema validators for the x & y coordinates

(defn set-start-point!
  "Sets the starting point coordinates i.e. the location where user clicks for the first time in the drawing-area"
  [x]
  {:pre [(s/validate point-schema x)]}
  (reset! start-point x))


(defn set-end-point!
  "Sets the ending point coordinates i.e. the location where user clicks for the second time in the drawing-area"
  [x]
  {:pre [(s/validate point-schema x)]}
  (reset! end-point x))

;; Helper Function
;; Calculate radius of circle

(defn calculate-radius
  "Calculate the radius of the circle with start-point(x1,y1) & end-point(x2,y2)"
  []
  (let [start-x (:x @start-point)
        start-y (:y @start-point)
        end-x (:x @end-point)
        end-y (:y @end-point)
        dx (- end-x start-x)
        dy (- end-y start-y)
        dx-square (Math/pow dx 2)
        dy-square (Math/pow dy 2)
        radius (Math/sqrt (+ dx-square dy-square))]
    radius))

;; Functions handling different drawing states -
;;  1) start drawing 2) mouse moving (temporary state) 3) finish drawing

(defn start-drawing!
  "Captures the coordinates of user's first click on drawing-area i.e. starting point's x & y coordinates"
  [x]
  (let [start-x (-> x .-clientX)
        start-y (-> x .-clientY)
        start-point {:x start-x :y start-y}]
   (set-start-point! start-point)
   (reset! clicked?  true)))

(defn handle-mouse-moving!
  "Captures the coordinates of drawing area when the user is moving around i.e. captures temporary x & y coordinates until the second click is perfomed"
  [x]
  (let [end-x (-> x .-clientX)
        end-y (-> x .-clientY)
        end-point {:x end-x :y end-y}]
    (set-end-point! end-point)))

(defn finish-drawing!
  "Captures the coordinates of user's second click on drawing-area i.e. end point x & y coordinates"
  [x]
  (let [end-x (-> x .-clientX)
        end-y (-> x .-clientY)
        end-point {:x end-x :y end-y}]
   (set-end-point! end-point)
   (reset! clicked?  false)))

;; Functions interpreting user-inputs

(defn draw-line
  "Handles line drawing based on user input"
  [x]
  (cond
    (= false @clicked?)(start-drawing! x)
    (= true  @clicked?)((finish-drawing! x)
                        (swap!  shape-list conj (vector :line {:x1 (:x @start-point)
                                                               :y1 (:y @start-point)
                                                               :x2 (:x @end-point)
                                                               :y2 (:y @end-point)}))
                        (reset! current-shape "none"))))

(defn draw-circle
  "Handles circle drawing based on user input"
  [x]
  (cond
    (= false @clicked?)(start-drawing! x)
    (= true @clicked?)((finish-drawing! x)
                       (let [radius (calculate-radius)]
                         (swap! shape-list conj (vector :circle {:cx (:x @start-point)
                                                                 :cy (:y @start-point)
                                                                 :fill "none"
                                                                 :r radius}))
                         (reset! current-shape "none")))))

(defn draw-rectangle
  "Handles rectangle drawing based on user input"
  [x]
  (cond
    (= false @clicked?)(start-drawing! x)
    (= true @clicked?)((finish-drawing! x)
                       (let [x1 (:x @start-point)
                             y1 (:y @start-point)
                             x2 (:x @end-point)
                             y2 (:y @end-point)
                             min-x (min x1 x2)
                             min-y (min y1 y2)
                             max-x (max x1 x2)
                             max-y (max y1 y2)
                             width (- max-x min-x)
                             height (- max-y min-y)]
                         (swap! shape-list conj (vector :rect {:x min-x
                                                               :y min-y
                                                               :width width
                                                               :height height
                                                               :fill "none"}))
                         (reset! current-shape "none")))))


;; Functions handling the display of moving shapes using variable user inputs

(defn moving-line
  "Handles the display of the temporary moving-line based on variable user input and captures location of second user click"
  [x]
  (when (= true @clicked?)
    ((handle-mouse-moving! x)
     (reset! current-shape (vector :line {:x1 (:x @start-point)
                                          :y1 (:y @start-point)
                                          :x2 (:x @end-point)
                                          :y2 (:y @end-point)})))))

(defn moving-circle
  "Handles the display of the temporary moving-circle based on variable user input and captures location of second user click"
  [x]
  (when (= true @clicked?)
    ((handle-mouse-moving! x)
     (let [radius (calculate-radius)]
      (reset! current-shape (vector :circle {:cx (:x @start-point)
                                             :cy (:y @start-point)
                                             :r radius
                                             :fill "none"}))))))

(defn moving-rectangle
  "Handles the display of the temporary moving-rectangle based on variable user input and captures location of second user click"
  [x]
  (when (= true @clicked?)
          ((handle-mouse-moving! x)
           (let [x1 (:x @start-point)
                 y1 (:y @start-point)
                 x2 (:x @end-point)
                 y2 (:y @end-point)
                 min-x (min x1 x2)
                 min-y (min y1 y2)
                 max-x (max x1 x2)
                 max-y (max y1 y2)
                 width (- max-x min-x)
                 height (- max-y min-y)]
             (reset! current-shape (vector :rect {:x min-x
                                                  :y min-y
                                                  :width width
                                                  :height height
                                                  :fill "none"}))))))

;; Functions handling various user clicks

(defn line-click
  "This function handles user clicking the line button"
  []
  (reset! shape :line))

(defn circle-click
  "This function handles user clicking the circle button"
  []
  (reset! shape :circle))

(defn rectangle-click
  "This function handles user clicking the rectangle button"
  []
  (reset! shape :rect))

(defn undo-click
  "This function handles user clicking the undo button"
  []
  (cond
    (= false  @clicked?)
      (let [mode (first (first @shape-list))]
        (if (= mode @shape)
            (swap!  shape-list rest @shape-list)
            (cond
              (= mode :line)(line-click)
              (= mode :circle)(circle-click)
              (= mode :rect)(rectangle-click))))
    (= true @clicked?)
       ((reset! current-shape "none")
        (reset! clicked?  false))))


(defn palette-click
  "This function handles user clicking on the palette"
  [x]
  (cond
    (= :line @shape)(draw-line x)
    (= :circle @shape)(draw-circle x)
    (= :rect @shape)(draw-rectangle x)))


(defn palette-move
  "This function handles user moving the mouse on the palette"
  [x]
  (cond
    (= :line @shape)(moving-line x)
    (= :circle @shape)(moving-circle x)
    (= :rect @shape)(moving-rectangle x)))

;; Function used to draw the UI

(defn drawing-area
  []
  (list
   [:svg {:id "drawing-area-svg"
          :key "draw-area"
          :width 700
          :height 550
          :stroke "black"
          :style {:top 0 :left 0 :border "red solid 1px"}
          :on-click palette-click
          :on-mouse-move palette-move}
    (list @shape-list)
    (list @current-shape)]))

(defn palette-buttons
  []
  (list
   [:div {:id "palette-buttons"}
   [:div {:id "palette-div" :key "palette" :style {:position :fixed :top 600 :left 100}}
      (if (= :line @shape)[:input {:id "line-button-clicked"
                                   :key "line-c"
                                   :on-click line-click
                                   :value (str "         LINE   ")}]
                          [:input {:id "line-button"
                                   :key "line"
                                   :on-click line-click
                                   :value (str "         LINE   ")}])
      (if (= :rect @shape)[:input {:id "rectangle-button-clicked"
                                   :key "rect-c"
                                   :on-click rectangle-click
                                   :value (str "        RECTANGLE   ")}]
                          [:input {:id "rectangle-button"
                                   :key "rect"
                                   :on-click rectangle-click
                                   :value (str "        RECTANGLE   ")}])
      (if (= :circle @shape)[:input {:id "circle-button-clicked"
                                     :key "circ-c"
                                     :on-click circle-click
                                     :value (str "       CIRCLE   ")}]
                            [:input {:id "circle-button"
                                     :key "circ"
                                     :on-click circle-click
                                     :value (str "       CIRCLE   ")}])]]))
(defn undo-button
  []
  (list
   [:div {:id "undo" :key "undo"}
      [:input {:id "undo-button"
               :key "undo"
               :type "button"
               :on-click undo-click
               :value (str " UNDO ")}]]))


(defn main []
  [:div {:id "main-div" :key "main-div"}
    (palette-buttons)
    (undo-button)
    [:div {:id "drawing-area-div" :key "draw-area-div"}
      (drawing-area)]])

(r/render-component [main]
  (. js/document (getElementById "app")))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

