┌──(kali㉿kali)-[~]
└─$ isim="ziya"                                                                             127 ⨯
                                                                                                  
┌──(kali㉿kali)-[~]
└─$ echo $isim                                                                
ziya

──(kali㉿kali)-[~]
└─$ isimsoyisin="$isim $soyisim"  
                                                                                                  
┌──(kali㉿kali)-[~]
└─$ echo $isimsoyisim

cd /etc/systemd/system 


LAB2

Q1) 

└─$ cat /etc/shells  
# /etc/shells: valid login shells
/bin/sh
/bin/bash
/usr/bin/bash
/bin/rbash
/usr/bin/rbash
/bin/dash
/usr/bin/dash
/bin/zsh
/usr/bin/zsh
/usr/bin/tmux
/usr/bin/screen
/usr/bin/pwsh
/opt/microsoft/powershell/7/pwsh
                                                                                                  
┌──(kali㉿kali)-[/etc/systemd/system]
└─$ printf "Mevcut kabuk - %s\n" "$SHELL"
Mevcut kabuk - /usr/bin/zsh
                              

chsh -s /bin/bash 
 echo $0
  bash


chsh -s $(which zsh)

┌──(kali㉿kali)-[~]
└─$ echo $0                        
/usr/bin/zsh


Q2)




Q3)
Kalıcı olması için bizim terminal dosyamıza kaydfetmemiz lazım. Örneğin benimki bash .bash dosyasına eklersem kalıcı olur.

Q4)
.bash_history de depolanır

