![Static Badge](https://img.shields.io/badge/Java-17-blue)
![Static Badge](https://img.shields.io/badge/Swing-blue)
![Static Badge](https://img.shields.io/badge/Lombok-blue)
![Static Badge](https://img.shields.io/badge/Maven-blue)

## Игра "Сапёр"
<picture align="center">
    <img alt="sample" src="./assets/process.png" width="50%" height="50%" />
    <img alt="sample" src="./assets/win.png" width="50%" height="50%" />
    <img alt="sample" src="./assets/fault.png" width="50%" height="50%" />
</picture>



### Описание
 - левая кнопка мыши: открыть ячейку
 - правая кнопка мыши: пометить ячейку
 - средняя кнопка мыши: перезапуск

### Настройки
 - `Фактор бомб: [0.1 - 1], default:[0.15]`
   - количество бомб вычисляется как: `[cols]*[rows]*[Фактор бомб]`

### Установка
```
git clone https://github.com/igojig/sweeper
```

### Запуск
```
mvn clean compile
mvn exec:java
```

### Примечание
В проекте используется `IntelliJ Designer Forms`, для корректной сборки добавлен соответствующий `Maven-plugin`