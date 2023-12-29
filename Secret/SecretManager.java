import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

enum TagType {
    Private, Protected, Own, Public
}

class SecretData {
    String value;
    TagType type;

    public SecretData(String value, TagType type) {
        this.value = value;
        this.type = type;
    }
}

public class SecretManager {
    private String filePath;

    public SecretManager(String filePath) {
        this.filePath = filePath;
    }

    private Map<String, SecretData> readSecretFile() {
        try {
            Map<String, SecretData> secrets = new HashMap<>();
            Files.lines(Paths.get(filePath)).forEach(line -> {
                String[] parts = line.split("<|>");
                String key = parts[1];
                String value = parts[2];
                TagType type = TagType.valueOf(parts[3]);
                secrets.put(key, new SecretData(value, type));
            });
            return secrets;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }

    private void writeSecretFile(Map<String, SecretData> secrets) {
        try {
            StringBuilder content = new StringBuilder();
            for (Map.Entry<String, SecretData> entry : secrets.entrySet()) {
                content.append("<").append(entry.getKey()).append(">").append(entry.getValue().value)
                        .append("<").append(entry.getValue().type).append(">\n");
            }
            Files.write(Paths.get(filePath), content.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        Map<String, SecretData> secrets = readSecretFile();
        SecretData secret = secrets.get(key);
        return (secret != null && secret.type == TagType.Private) ? null : secret.value;
    }

    public void set(String key, String value, TagType type) {
        Map<String, SecretData> secrets = readSecretFile();
        secrets.put(key, new SecretData(value, type));
        writeSecretFile(secrets);
    }

    public void delete(String key) {
        Map<String, SecretData> secrets = readSecretFile();
        secrets.remove(key);
        writeSecretFile(secrets);
    }

    public void update(String key, String value, TagType type) {
        Map<String, SecretData> secrets = readSecretFile();
        if (secrets.containsKey(key)) {
            secrets.put(key, new SecretData(value, type));
            writeSecretFile(secrets);
        }
    }
}
