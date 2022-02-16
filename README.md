# ClipHero
Program to share Clipboards

## Entstehung
Aktuell lerne ich Kotlin. Deshalb entwickle ich gerade ein kleines Programm, mit dem man ohne viel Aufwand seine Zwischenablage mit anderen teilen und synchronisieren kann. Es wird auch eine Historie der letzten geteilten Zwischenablagen geführt. Das geht aktuell nur auf Rechnern mit Java, eine Android App soll aber noch folgen.
Das Programm ist natürlich noch in der Entwicklung, also funktioniert ein Großteil der Funktionen noch nicht.

## Funktionsweise

### Datenverkehr
Die Daten werden über einen UDP Multicast geteilt. Man benötigt also keinen Server. Der Nachteil ist jedoch, dass es nur innerhalb des lokalen Netzwerks funktioniert und der Datenverkehr völlig unverschlüsselt ist.
Es wird der Port 50111 verwendet. Ich werde auch noch eine Option zur Änderung des Ports implementieren.

### Gui
Ich verwende das Flat Look and Feel (https://github.com/JFormDesigner/FlatLaf).
Beim Schließen des Programms wird es im System Tray minimiert, um zu gewährleisten, dass die Zwischenablage weiterhin synchronisiert wird.
