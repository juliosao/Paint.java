Summary: A Simple drawing program written in java
Name: paint-java
Version: 0.1
Release: 1%{?dist}
URL: https://github.com/juliosao/Paint.java
License: Privative
ExclusiveOS: Linux
Group: Desktop/Graphics
BuildRoot: %{_tmppath}/%{name}-%{version}-root
BuildArch: noarch
Source0: %{name}-%{version}.tar.bz2
Requires: java


%description
A Simple drawing program written in java. This program aims to be compatible with original formats from DivGamesStudio.

%prep
%setup -q

%install
mkdir -p $RPM_BUILD_ROOT/opt/sao/paintjava/
cp LICENSE $RPM_BUILD_ROOT/opt/sao/paintjava/
cp README.md $RPM_BUILD_ROOT/opt/sao/paintjava/
cp paintjava.sh $RPM_BUILD_ROOT/opt/sao/paintjava/
cp paintjava.jar $RPM_BUILD_ROOT/opt/sao/paintjava/

mkdir -p $RPM_BUILD_ROOT/usr/share/applications
cp paintjava.desktop  $RPM_BUILD_ROOT/usr/share/applications

%clean
rm -rf $RPM_BUILD_ROOT

%files
%defattr(444,root,root,555)
/opt/sao/paintjava/paintjava.jar
/opt/sao/paintjava/LICENSE
/opt/sao/paintjava/README.md
%attr(555,root,root)/opt/sao/paintjava/paintjava.sh
/usr/share/applications/paintjava.desktop

%changelog
* Fri Dec 17 2021 Julio A. García <julio.garcia@afronta.com> 0.1
- First release
