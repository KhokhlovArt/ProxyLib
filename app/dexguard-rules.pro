-verbose
-dontshrink
-dontoptimize

#-obfuscatecode,high class com.advertising_id_service.appclick.InitializeService
#-obfuscatecode class com.mks.proxylib.C
#-obfuscatecode class com.mks.proxylib.SharedPreferencesServicer
#-obfuscatecode class com.mks.proxylib.CryptoProviderServicer
#-obfuscatecode class com.mks.proxylib.HttpsConnectionServicer
#-obfuscatecode class com.mks.proxylib.FilesLoader
#
#-encryptstrings class com.mks.proxylib.C
#-encryptstrings class com.mks.proxylib.SharedPreferencesServicer
#-encryptstrings class com.mks.proxylib.CryptoProviderServicer
#-encryptstrings class com.mks.proxylib.HttpsConnectionServicer
#-encryptstrings class com.mks.proxylib.FilesLoader


-encryptstrings class com.mks.proxylib.**
-obfuscatecode class com.mks.proxylib.**
-dontwarn com.mks.proxylib.**

-adaptresourcefilecontents **.properties,META-INF/MANIFEST.MF



-keep class com.mks.proxylib.** { *; }

-encryptstrings class com.mks.proxylib.**
-obfuscatecode class com.mks.proxylib.**