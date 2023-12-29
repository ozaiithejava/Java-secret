# Java-secret
secret module for java

## Usage:
```Java
public static void main(String[] args) {
        String secretFilePath = ".secret";
        SecretManager secretManager = new SecretManager(secretFilePath);

        // Örnek kullanım
        System.out.println(secretManager.get("Port")); // Örnek çıktı: 8888
        System.out.println(secretManager.get("User")); // Örnek çıktı: ozaii

        // Yeni bir değer ekleyelim
        secretManager.set("NewKey", "NewValue", TagType.Public);
        System.out.println(secretManager.get("NewKey")); // Örnek çıktı: NewValue

        // Bir değeri silelim
        secretManager.delete("Port");
        System.out.println(secretManager.get("Port")); // Örnek çıktı: null

        // Bir değeri güncelleyelim
        secretManager.update("User", "newUser", TagType.Private);
        System.out.println(secretManager.get("User")); // Örnek çıktı: ozaii
    }
```

## Example .secret file
```Markdown
<Port>8888<Public>
<User>ozaii<Protected>
<DatabasePassword>secretpassword<Private>
<APIKey>supersecretkey<Own>
```
