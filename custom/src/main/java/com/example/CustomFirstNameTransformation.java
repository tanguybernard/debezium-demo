package com.example;

import org.apache.kafka.connect.connector.ConnectRecord;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.transforms.Transformation;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.data.Struct;

import java.util.HashMap;
import java.util.Map;

public class CustomFirstNameTransformation<R extends ConnectRecord<R>> implements Transformation<R> {

    @Override
    public R apply(R record) {

        if (record.value() instanceof Struct) {
            Struct l = (Struct) record.value();
            Struct after;
            try {
                after = l.getStruct("after");
                String lastName = after.getString("last_name");

                if (lastName != null) {
                    // Ajouter le suffixe
                    String transformedLastName = lastName + "_transformed";

                    Struct updatedAfter = new Struct(after.schema());

                    // Copier les autres champs
                    for (Field field : after.schema().fields()) {
                        if ("last_name".equals(field.name())) {
                            updatedAfter.put("last_name", transformedLastName);
                        } else {
                            updatedAfter.put(field.name(), after.get(field));
                        }
                    }

                    // Créer une nouvelle instance du record avec les modifications
                    Struct updatedValue = new Struct(l.schema());
                    updatedValue.put("after", updatedAfter);

                    for (Field field : l.schema().fields()) {
                        if (!"after".equals(field.name())) {
                            updatedValue.put(field.name(), l.get(field));
                        }
                    }
                    return record.newRecord(
                            record.topic(),
                            record.kafkaPartition(),
                            record.keySchema(),
                            record.key(),
                            record.valueSchema(),
                            updatedValue,
                            record.timestamp()

                    );
                }
            }
            catch (Exception e) {
                System.out.println(e);
                return record;
            }
        }
        return record;
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef();
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

    @Override
    public void close() {
    }
    public String version() {
        return "1.0";
    }
}