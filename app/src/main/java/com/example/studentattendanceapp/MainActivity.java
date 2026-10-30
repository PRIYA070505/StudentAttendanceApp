
        package com.example.studentattendanceapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText nameInput, rollInput;
    TextView recordsView, countView;

    ArrayList<String> records = new ArrayList<>();

    int presentCount = 0;
    int absentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        rollInput = findViewById(R.id.rollInput);

        recordsView = findViewById(R.id.recordsView);
        countView = findViewById(R.id.countView);

        Button presentButton = findViewById(R.id.presentButton);
        Button absentButton = findViewById(R.id.absentButton);
        Button clearButton = findViewById(R.id.clearButton);

        presentButton.setOnClickListener(v ->
                addAttendance("Present"));

        absentButton.setOnClickListener(v ->
                addAttendance("Absent"));

        clearButton.setOnClickListener(v -> {
            nameInput.setText("");
            rollInput.setText("");
        });

        updateDisplay();
    }

    private void addAttendance(String status) {

        String name = nameInput.getText().toString().trim();
        String roll = rollInput.getText().toString().trim();

        if (name.isEmpty() || roll.isEmpty()) {
            Toast.makeText(this,
                    "Please enter name and roll number",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        for (String record : records) {
            String[] parts = record.split("\\|");

            if (parts.length >= 2 &&
                    parts[1].trim().equalsIgnoreCase(roll)) {

                Toast.makeText(this,
                        "Roll number already exists",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String record = (records.size() + 1) + ". "
                + name + " | Roll: " + roll + " | " + status;

        records.add(record);

        if (status.equals("Present")) {
            presentCount++;
        } else {
            absentCount++;
        }

        updateDisplay();

        nameInput.setText("");
        rollInput.setText("");

        Toast.makeText(this,
                "Attendance marked: " + status,
                Toast.LENGTH_SHORT).show();
    }

    private void updateDisplay() {

        if (records.isEmpty()) {
            recordsView.setText("No records yet.");
        } else {
            StringBuilder builder = new StringBuilder();

            for (String record : records) {
                builder.append(record).append("\n");
            }

            recordsView.setText(builder.toString().trim());
        }

        countView.setText(
                "Present: " + presentCount
                        + " | Absent: " + absentCount
                        + " | Total: " + records.size()
        );
    }
}

