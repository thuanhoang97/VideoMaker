echo y | ffmpeg -loop 1 -i "C:\Users\Rem\Desktop\test\image.jpg" -i "C:\Users\Rem\Desktop\test\Dung-Nhu-Thoi-Quen-JayKii-Sara-Luu.mp3" -vf drawtext='fontfile="Arial"\:style=regular:fontsize=100:textfile="D\:/REM/Java/VideoScroll/-829169424.txt":fontcolor=#FFFFFF':x=0:y=h-t*200.0,format=yuv420p,scale=852x480,setsar=1:1 -t 10.0 -vcodec libx264 -b:v 1000k -preset superfast "C:\Users\Rem\Desktop\test\video\folder_o.mp4"
echo y | ffmpeg -i "C:\Users\Rem\Desktop\test\aaaa.mp4"  -i "C:\Users\Rem\Desktop\test\video\folder_o.mp4" -filter_complex "[0:v:0] [0:a:0] [1:v:0] [1:a:0] concat=n=2:v=1:a=1 [v] [a]"  -map "[v]" -map "[a]" "C:\Users\Rem\Desktop\test\video\folder.mp4" 
pause