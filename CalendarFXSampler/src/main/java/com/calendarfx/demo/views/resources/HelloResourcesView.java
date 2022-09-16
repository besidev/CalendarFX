/*
 *  Copyright (C) 2017 Dirk Lemmermann Software & Consulting (dlsc.com)
 *  Copyright (C) 2006 Google Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.calendarfx.demo.views.resources;

import com.calendarfx.demo.CalendarFXDateControlSample;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.DayViewBase.AvailabilityEditingEntryBehaviour;
import com.calendarfx.view.DayViewBase.EarlyLateHoursStrategy;
import com.calendarfx.view.resources.Resource;
import com.calendarfx.view.resources.ResourcesView;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

public class HelloResourcesView extends CalendarFXDateControlSample {

    private ResourcesView resourcesView;

    @Override
    public String getSampleName() {
        return "ResourcesView";
    }

    @Override
    public String getSampleDescription() {
        return "The detailed day view aggregates a day view, an all day view, a calendar header (for swimlane layout), and a time scale.";
    }

    @Override
    protected Class<?> getJavaDocClass() {
        return ResourcesView.class;
    }

    @Override
    protected boolean isSupportingDeveloperConsole() {
        return false;
    }

    @Override
    public Node getControlPanel() {
        ToggleButton availabilityButton = new ToggleButton("Edit Schedule");
        availabilityButton.selectedProperty().bindBidirectional(resourcesView.editAvailabilityProperty());

        DatePicker datePicker = new DatePicker();
        datePicker.valueProperty().bindBidirectional(resourcesView.dateProperty());

        ChoiceBox<Integer> daysBox = new ChoiceBox<>();
        daysBox.getItems().setAll(1, 2, 3, 4, 5, 7, 10, 14);
        daysBox.setValue(resourcesView.getNumberOfDays());
        daysBox.valueProperty().addListener(it -> resourcesView.setNumberOfDays(daysBox.getValue()));

        ChoiceBox<AvailabilityEditingEntryBehaviour> behaviourBox = new ChoiceBox<>();
        behaviourBox.getItems().setAll(AvailabilityEditingEntryBehaviour.values());
        behaviourBox.valueProperty().bindBidirectional(resourcesView.entryViewAvailabilityEditingBehaviourProperty());

        CheckBox adjustBox = new CheckBox("Adjust first day of week");
        adjustBox.selectedProperty().bindBidirectional(resourcesView.adjustToFirstDayOfWeekProperty());

        Slider slider = new Slider();
        slider.setMin(0);
        slider.setMax(1);
        slider.valueProperty().bindBidirectional(resourcesView.entryViewAvailabilityEditingOpacityProperty());

        return new VBox(10, availabilityButton, datePicker, adjustBox, daysBox, new Label("Availability Behaviour"), behaviourBox, new Label("Availability Opacity"), slider);
    }

    @Override
    protected DateControl createControl() {
        resourcesView = new ResourcesView();
        resourcesView.setNumberOfDays(5);
        resourcesView.setEarlyLateHoursStrategy(EarlyLateHoursStrategy.HIDE);
        resourcesView.getResources().addAll(create("Dirk", Style.STYLE1), create("Katja", Style.STYLE2), create("Philip", Style.STYLE3)); //, create("Jule", Style.STYLE4), create("Armin", Style.STYLE5));
        return resourcesView;
    }

    private Resource<String> create(String name, Style style) {
        Resource<String> resource = new Resource(name);
        resource.getAvailabilityCalendar().setName("Availability of " + name);
        resource.getCalendar().setStyle(style);
        resource.getCalendar().addEventHandler(evt -> System.out.println(evt));
        resource.getAvailabilityCalendar().addEventHandler(evt -> System.out.println(evt));
        return resource;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
