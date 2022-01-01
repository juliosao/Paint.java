# Paint.java
A Simple drawing program written in java. This program aims to be compatible with original formats from DivGamesStudio

## Tools
At the present, these tools are implemented:
- Pencil
- Line
- Curve
- Rectangle
- Ellipse
- Text
- Brush
- Airbrush
- Erarser
- Flood Fill
- Color selection
- Pencil weight selection
- Zoom
- Color Picker
- Smudge
- Blur
- Sharpen
- Clone
- Color Gamma: When a color from a palette is choosed, entire gamma is loaded into main UI
- Copy and paste
- Undo and redo changes

## Filters
There are many filters implemented:
- Blur
- Dark
- Drop color
- Edge detect
- Gray scale
- Highrelief
- Light
- Sharpen
- Invert colors
- Drop colors

## File formats
At the moment, these image formats are supported:
- BMP
- PNG
- JPEG

Div Games Studio formats supported:
- PAL (Read)
- MAP (Read)

## Building
Before of all, you need java 1.8+ and ant to build paint.java. You can download ant from https://ant.apache.org/bindownload.cgi

To build paint .java execute ''ant''

## Make a installation package
### MSI package
Before to build an msi package you need at least java 14, ant and wix. You can download wix from https://github.com/wixtoolset

To build a msi installer execute this command:
  ''ant msi''

If all of task works ok, a "paint.java-X.Y.msi" file will appear in your working directory

### RPM package
You can create an RPM package only on linux distributions with the tool "rpmbuild" installed. Query your system documentation in order to know how to install it.

To build a msi installer execute this command:
  ''ant rpm''

If all of task works ok, a "paint.java-X.Y.rpm" file will appear in your rpmbuild directory

### DEB package
You can create an RPM package only on linux distributions with the tool "dpkg-deb" installed. Query your system documentation in order to know how to install it.

To build a msi installer execute this command:
  ''ant deb''

If all of task works ok, a "paint.java-X.Y.deb" file will appear in your dist directory

