<?xml version="1.0" encoding="utf-8"?>
<!-- Copyright (C) 2014 The Android Open Source Project

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.
-->

<!--
This is an optimized layout for a screen, with the minimum set of features
enabled.
-->

<androidx.appcompat.widget.FitWindowsLinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:orientation="vertical"
        android:fitsSystemWindows="true">

    <TextView
            android:id="@+id/title"
            style="?android:attr/windowTitleStyle"
            android:singleLine="true"
            android:ellipsize="end"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="start"
            android:textAlignment="viewStart"
            android:paddingLeft="?attr/dialogPreferredPadding"
            android:paddingRight="?attr/dialogPreferredPadding"
            android:paddingTop="@dimen/abc_dialog_padding_top_material"/>

    <include
            layout="@layout/abc_screen_content_include"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"/>

</androidx.appcompat.widget.FitWindowsLinearLayout>                                                                                                                                                                                                                                                                                                                                                                                                  