package com.football5.game
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

data class Q(val q:String,val a:String)
private val bank=listOf(
Q("دو باشگاه پله؟","سانتوس، نیویورک کاسموس"),
Q("سه باشگاه مسی؟","بارسلونا، پاری‌سن‌ژرمن، اینتر میامی"),
Q("چهار باشگاه کریستیانو رونالدو؟","اسپورتینگ، منچستریونایتد، رئال مادرید، یوونتوس، النصر"),
Q("سه کشور قهرمان جام جهانی؟","برزیل، آلمان، ایتالیا، آرژانتین، فرانسه، اروگوئه، انگلیس، اسپانیا"),
Q("سه برنده توپ طلا؟","مسی، رونالدو، زیدان، مودریچ، کاکا، بنزما"),
Q("سه دروازه‌بان مشهور؟","بوفون، کاسیاس، نویر، یاشین، چک"),
Q("سه مدافع مشهور؟","مالدینی، راموس، بکن‌باوئر، کاناوارو، نستا"),
Q("سه هافبک مشهور؟","ژاوی، اینیستا، پیرلو، زیدان، مودریچ"),
Q("سه مهاجم مشهور؟","رونالدو نازاریو، هانری، لواندوفسکی، سوارز، اتوئو"),
Q("سه باشگاه ایتالیایی؟","یوونتوس، میلان، اینتر، رم، ناپولی"),
Q("سه باشگاه انگلیسی؟","منچستریونایتد، لیورپول، آرسنال، چلسی، منچسترسیتی"),
Q("سه باشگاه اسپانیایی؟","رئال مادرید، بارسلونا، اتلتیکو، سویا، والنسیا"),
Q("سه باشگاه آلمانی؟","بایرن، دورتموند، لورکوزن، لایپزیگ"),
Q("سه باشگاه فرانسوی؟","پاری‌سن‌ژرمن، مارسی، لیون، موناکو"),
Q("سه تیم ملی آمریکای جنوبی؟","برزیل، آرژانتین، اروگوئه، کلمبیا، شیلی"),
Q("سه تیم ملی آسیایی؟","ژاپن، کره جنوبی، ایران، عربستان، استرالیا"),
Q("سه بازیکن آرژانتینی؟","مسی، مارادونا، دی‌ماریا، باتیستوتا، آگوئرو"),
Q("سه بازیکن برزیلی؟","پله، رونالدو، رونالدینیو، نیمار، کاکا"),
Q("سه بازیکن پرتغالی؟","رونالدو، اوزه‌بیو، فیگو، برونو فرناندز"),
Q("سه بازیکن فرانسوی؟","زیدان، امباپه، هانری، ویرا، بنزما"),
Q("سه بازیکن آلمانی؟","بکن‌باوئر، کلوزه، ماتئوس، نویر، توماس مولر"),
Q("سه بازیکن هلندی؟","کرایف، فان‌باستن، روبن، فان‌دایک، اسنایدر"),
Q("سه بازیکن انگلیسی؟","بکام، جرارد، لمپارد، کین، رونی"),
Q("سه بازیکن ایتالیایی؟","مالدینی، بوفون، پیرلو، توتی، دل‌پیرو"),
Q("سه بازیکن قهرمان جام جهانی ۲۰۱۴؟","نویر، مولر، اوزیل، کلوزه، لام، کروس"),
Q("سه بازیکن قهرمان جام جهانی ۲۰۱۸؟","امباپه، گریزمان، پوگبا، کانته، ژیرو"),
Q("سه بازیکن قهرمان جام جهانی ۲۰۲۲؟","مسی، دی‌ماریا، امیلیانو مارتینز، آلوارز"),
Q("سه ورزشگاه مشهور؟","ومبلی، برنابئو، سن‌سیرو، ماراکانا، نیوکمپ"),
Q("سه رقابت ملی فوتبال؟","جام جهانی، یورو، کوپا آمریکا، جام ملت‌های آسیا"),
Q("سه رقابت باشگاهی؟","لیگ قهرمانان، لیگ اروپا، لیبرتادورس"),
Q("سه مربی مشهور؟","فرگوسن، گواردیولا، مورینیو، آنچلوتی، ونگر"),
Q("سه بازیکن مشترک رئال و منچستریونایتد؟","رونالدو، بکام، واران، کاسمیرو"),
Q("سه بازیکن مشترک بارسا و پاریس؟","مسی، نیمار، رونالدینیو، دنی آلوز"),
Q("سه بازیکن مشترک اینتر و میلان؟","زلاتان، رونالدینیو، پیرلو، سیدورف"),
Q("سه بازیکن مشترک آرسنال و چلسی؟","اشلی کول، فابرگاس، چک، ژیرو"),
Q("سه بازیکن مشترک لیورپول و بارسا؟","سوارز، ماسکرانو، کوتینیو"),
Q("سه بازیکن برنده کفش طلای جام جهانی؟","رونالدو نازاریو، توماس مولر، هری کین، خامس، امباپه"),
Q("سه بازیکن که در جام جهانی هت‌تریک کردند؟","جف هرست، باتیستوتا، کلوزه، امباپه"),
Q("سه بازیکن با بیش از یک توپ طلا؟","مسی، رونالدو، کرایف، پلاتینی، فان‌باستن"),
Q("سه بازیکن مشهور شماره ۱۰؟","مسی، مارادونا، پله، زیدان، رونالدینیو"),
Q("سه بازیکن مشهور شماره ۷؟","رونالدو، بکام، کانتونا، رائول، فیگو"),
Q("سه بازیکن مشهور شماره ۹؟","رونالدو نازاریو، لواندوفسکی، سوارز، بنزما، هالند"),
Q("سه بازیکن قهرمان جام جهانی و لیگ قهرمانان؟","مسی، زیدان، رونالدو نازاریو، کروس، ژاوی"),
Q("سه بازیکن برزیل در قهرمانی ۲۰۰۲؟","رونالدو، رونالدینیو، ریوالدو، کافو، روبرتو کارلوس"),
Q("سه بازیکن منچستریونایتد در دوران فرگوسن؟","رونی، گیگز، اسکولز، بکام، رونالدو"),
Q("سه بازیکن آرسنال در دوران ونگر؟","هانری، برگکمپ، ویرا، فابرگاس، اوزیل"),
Q("سه بازیکن چلسی در دوران مورینیو؟","لمپارد، دروگبا، تری، چک، هازارد"),
Q("سه بازیکن بارسلونا در دوران پپ؟","مسی، ژاوی، اینیستا، بوسکتس، پویول"),
Q("سه بازیکن ایرانی شناخته‌شده؟","علی دایی، مهدوی‌کیا، علی کریمی، آزمون، طارمی"),
Q("سه باشگاه ایرانی؟","پرسپولیس، استقلال، سپاهان، تراکتور، فولاد"),
Q("سه کشور قهرمان یورو؟","آلمان، اسپانیا، ایتالیا، فرانسه، پرتغال، هلند"),
Q("سه کشور قهرمان کوپا آمریکا؟","آرژانتین، اروگوئه، برزیل، شیلی، کلمبیا"),
Q("سه کشور قهرمان جام ملت‌های آسیا؟","ژاپن، عربستان، ایران، کره جنوبی، استرالیا"),
Q("سه بازیکن قهرمان جام جهانی ۲۰۱۰؟","اینیستا، ژاوی، کاسیاس، پویول، بوسکتس"),
Q("سه بازیکن قهرمان جام جهانی ۲۰۰۶؟","بوفون، کاناوارو، پیرلو، توتی، دل‌پیرو"),
Q("سه بازیکن قهرمان جام جهانی ۱۹۹۸؟","زیدان، دشان، هنری، تورام، ترزگه"),
Q("سه بازیکن مشهور آفریقایی؟","دروگبا، اتوئو، جورج وه‌آ، صلاح، مانه"),
Q("سه بازیکن مشهور آسیایی؟","سون، علی دایی، مهدوی‌کیا، ناکاتا، هوندا"),
Q("سه باشگاه شهر لندن؟","آرسنال، چلسی، تاتنهام، وستهم، کریستال پالاس"),
Q("دو باشگاه شهر منچستر؟","منچستریونایتد، منچسترسیتی"),
Q("دو باشگاه شهر میلان؟","اینتر، آ.ث. میلان"),
Q("سه باشگاه شهر مادرید؟","رئال، اتلتیکو، رایو وایکانو، ختافه"),
Q("دو باشگاه شهر مونیخ؟","بایرن، مونیخ ۱۸۶۰"),
Q("سه بازیکن که در دو جام جهانی متوالی گل زدند؟","مسی، رونالدو نازاریو، کلوزه، امباپه"),
Q("سه بازیکن مشترک بارسا و اینتر؟","رونالدینیو، زلاتان، اتوئو، فیگو"),
Q("سه بازیکن مشترک میلان و رئال؟","کاکا، رونالدو نازاریو، سیدورف، بکام"),
Q("سه بازیکن مشترک لیورپول و چلسی؟","تورس، صلاح، استرلینگ، استوریج"),
Q("سه بازیکن مشترک آرسنال و بارسا؟","هانری، هلب، فرمائلن، فابرگاس"),
Q("سه بازیکن مشترک رئال و بارسا؟","فیگو، رونالدو نازاریو، اتوئو، ساویولا"),
Q("سه گلزن فینال جام جهانی ۲۰۲۲؟","مسی، دی‌ماریا، امباپه"),
Q("سه گلزن فینال جام جهانی ۲۰۱۸؟","مانژوکیچ، گریزمان، پوگبا، امباپه"),
Q("سه بازیکن ایران در جام جهانی ۲۰۲۲؟","بیرانوند، طارمی، آزمون، حاج‌صفی، رضاییان"),
Q("سه بازیکن مشترک یوونتوس و رئال؟","زیدان، رونالدو، کاناوارو، خدیرا"),
Q("سه بازیکن مشترک بایرن و رئال؟","کروس، خامس، آلابا، روبن"),
Q("سه بازیکن مشترک چلسی و رئال؟","هازارد، کورتوا، روبن، موراتا"),
Q("سه بازیکن مشترک منچسترسیتی و بارسا؟","یایا توره، گوندوغان، براوو، فران تورس"),
Q("سه بازیکن مشترک منچستریونایتد و یووه؟","رونالدو، پوگبا، اورا، دی‌ماریا"),
Q("سه کشور میزبان جام جهانی؟","آلمان، برزیل، روسیه، قطر، آفریقای جنوبی، آمریکا"),
Q("سه بازیکن مشهور اروپای شرقی؟","مودریچ، شوچنکو، ندود، استویچکوف"),
Q("سه بازیکن که در فینال جام جهانی بازی کردند؟","مسی، زیدان، رونالدو، اینیستا، پله"),
Q("سه برنده جایزه بهترین بازیکن فیفا؟","مسی، رونالدو، لواندوفسکی، مودریچ، وینیسیوس")
)

class MainActivity:ComponentActivity(){
 override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{MaterialTheme{Game()}}}
}
@Composable
private fun Game(){
 val bg=Color(0xFF101820); val panel=Color(0xFF1D2A35); val lime=Color(0xFFB7F34A)
 var n1 by remember{mutableStateOf("بازیکن ۱")}; var n2 by remember{mutableStateOf("بازیکن ۲")}
 var s1 by remember{mutableIntStateOf(0)}; var s2 by remember{mutableIntStateOf(0)}
 var turn by remember{mutableIntStateOf(0)}; var secs by remember{mutableIntStateOf(5)}; var left by remember{mutableIntStateOf(5)}
 var target by remember{mutableIntStateOf(30)}; var rounds by remember{mutableIntStateOf(5)}; var byTarget by remember{mutableStateOf(false)}
 var round by remember{mutableIntStateOf(1)}; var used by remember{mutableStateOf(setOf<Int>())}; var q by remember{mutableStateOf<Q?>(null)}
 var phase by remember{mutableStateOf("setup")}; var result by remember{mutableStateOf("")}; var answers by remember{mutableStateOf(false)}; var tick by remember{mutableIntStateOf(0)}
 LaunchedEffect(phase,tick){
  if(phase=="play"){val start=System.currentTimeMillis();while(phase=="play"){left=(secs-((System.currentTimeMillis()-start)/1000L).toInt()).coerceAtLeast(0);if(left==0){phase="grace";break};if(left<=2)try{ToneGenerator(AudioManager.STREAM_ALARM,80).startTone(ToneGenerator.TONE_PROP_BEEP,100)}catch(_:Exception){};delay(100)}}
  else if(phase=="grace"){for(i in 3 downTo 1){left=i;delay(1000)};if(phase=="grace"){result="اتمام وقت";phase="result"}}
 }
 fun start(){val a=bank.indices.filter{it !in used};if(a.isEmpty()){phase="done";return};val i=a[Random.nextInt(a.size)];used=used+i;q=bank[i];left=secs;answers=false;phase="play";tick++}
 fun mark(ok:Boolean){if(phase!="play"&&phase!="grace")return;if(ok){val f=if(phase=="play")left.toFloat()/secs else 0f;val pts=3+(f*2).toInt()+(f*5).toInt();if(turn==0)s1+=pts else s2+=pts;result="درست! +"+pts+" امتیاز"}else result="پاسخ نادرست";phase="result"}
 fun next(){val end=if(byTarget)s1>=target||s2>=target else round>=rounds*2;if(end||used.size>=bank.size)phase="done" else{turn=1-turn;round++;phase="ready"}}
 fun reset(){s1=0;s2=0;turn=0;round=1;used=emptySet();q=null;phase="setup";result=""}
 Surface(Modifier.fillMaxSize(),color=bg){
  LazyColumn(Modifier.fillMaxSize().padding(16.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(12.dp)){
   item{Text("⚽ ۵ ثانیه فوتبالی",color=lime,fontSize=28.sp,fontWeight=FontWeight.Black);Text("سه جواب درست در زمان محدود؛ رقابت دونفره",color=Color.LightGray)}
   if(phase=="setup")item{
    Card(colors=CardDefaults.cardColors(containerColor=panel)){Column(Modifier.fillMaxWidth().padding(16.dp),verticalArrangement=Arrangement.spacedBy(9.dp)){
     Text("تنظیم مسابقه",color=Color.White,fontSize=20.sp)
     OutlinedTextField(n1,{n1=it},label={Text("نام بازیکن اول")},singleLine=true)
     OutlinedTextField(n2,{n2=it},label={Text("نام بازیکن دوم")},singleLine=true)
     Text("زمان هر نوبت",color=Color.White);Row{listOf(5,6,7).forEach{FilterChip(secs==it,{secs=it;left=it},label={Text(it.toString()+" ثانیه")})}}
     Row(verticalAlignment=Alignment.CenterVertically){RadioButton(!byTarget,{byTarget=false});Text("تعداد دور",color=Color.White);RadioButton(byTarget,{byTarget=true});Text("امتیاز هدف",color=Color.White)}
     if(byTarget){Text("امتیاز هدف: "+target,color=Color.White);Slider(target.toFloat(),{target=it.toInt()},valueRange=10f..100f,steps=8)}
     else{Text("تعداد دور هر بازیکن: "+rounds,color=Color.White);Slider(rounds.toFloat(),{rounds=it.toInt()},valueRange=3f..15f,steps=11)}
     Button({phase="ready"},Modifier.fillMaxWidth()){Text("شروع مسابقه")}
    }}
   }
   if(phase!="setup")item{Card(colors=CardDefaults.cardColors(containerColor=panel)){Column(Modifier.fillMaxWidth().padding(14.dp),horizontalAlignment=Alignment.CenterHorizontally){Text(n1+"  "+s1+" - "+s2+"  "+n2,color=Color.White,fontSize=19.sp,fontWeight=FontWeight.Bold);Text("نوبت "+(if(turn==0)n1 else n2)+" • دور "+round,color=lime)}}}
   if(phase=="ready")item{Button({start()},Modifier.fillMaxWidth()){Text("شروع سؤال")}}
   if(phase=="play"||phase=="grace"||phase=="result")item{
    Card(colors=CardDefaults.cardColors(containerColor=panel)){Column(Modifier.fillMaxWidth().padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally,verticalArrangement=Arrangement.spacedBy(12.dp)){
     Text("سؤال "+used.size+" از "+bank.size,color=lime);Text(q?.q.orEmpty(),color=Color.White,fontSize=22.sp,fontWeight=FontWeight.Bold)
     Text(if(phase=="grace")"۰" else left.toString(),color=if(left<=2)Color.Red else lime,fontSize=48.sp,fontWeight=FontWeight.Black)
     if(phase=="play"||phase=="grace"){Text("سه جواب را بگویید؛ داور نتیجه را ثبت کند.",color=Color.LightGray);Row(horizontalArrangement=Arrangement.spacedBy(10.dp)){Button({mark(true)},Modifier.weight(1f)){Text("درست")};Button({mark(false)},Modifier.weight(1f),colors=ButtonDefaults.buttonColors(containerColor=Color(0xFFC62828))){Text("غلط")}}}
     else{Text(result,color=lime,fontSize=22.sp);TextButton({answers=!answers}){Text(if(answers)"پنهان‌کردن جواب" else "نمایش جواب نمونه")};if(answers)Text(q?.a.orEmpty(),color=Color.White);Button({next()},Modifier.fillMaxWidth()){Text("نوبت بعدی")}}
    }}
   }
   if(phase=="done")item{Card(colors=CardDefaults.cardColors(containerColor=panel)){Column(Modifier.fillMaxWidth().padding(18.dp),horizontalAlignment=Alignment.CenterHorizontally){Text("مسابقه تمام شد",color=lime,fontSize=24.sp);Text(if(s1==s2)"مساوی!" else "برنده: "+if(s1>s2)n1 else n2,color=Color.White,fontSize=20.sp);Text(n1+": "+s1+" | "+n2+": "+s2,color=Color.White);Button({reset()}){Text("مسابقه جدید")}}}}
   item{Text("در هر مسابقه سؤال تکراری نمی‌آید. بانک سؤال نسخه اولیه است و پاسخ‌ها پیش از انتشار عمومی باید بازبینی شوند.",color=Color.Gray,fontSize=11.sp)}
  }
 }
}
