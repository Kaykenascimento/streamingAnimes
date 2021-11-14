package com.example.streaminganimes.activitys

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.firebase.ConfFireBase
import com.example.streaminganimes.models.ModelHistorico
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_assistir_ep.*
import kotlinx.android.synthetic.main.custom_controller_videoview.*

class AssistirEpActivity : AppCompatActivity() {

    private val db = ConfFireBase.firebaseFirestore!!
    private val auth = ConfFireBase.firebaseAuth!!
    private val episodiosDao = EpisodiosDao()
    private val usuarioDao = UsuarioDao()
    private var subtitle: Boolean = false
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var codigoAnime: String = ""
    private var codigoEp: String = ""
    private var tituloEp: String = ""
    private var link: String = ""
    private var tipo: String = ""
    private var imagem: String = ""
    private var saga: String = ""
    private var duracao: String = ""
    private var nomeAnime: String = ""
    private var sinopse: String = ""
    private var minutoAssistido: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistir_ep)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val bundle: Bundle? = this.intent.extras
        codigoAnime = bundle?.getString("codigoAnime")!!
        tituloEp = bundle?.getString("titulo")!!
        link = bundle?.getString("link")!!
        codigoEp = bundle?.getString("codigoEp")!!
        tipo = bundle?.getString("tipo")!!
        imagem = bundle?.getString("imagem")!!
        saga = bundle?.getString("saga")!!
        duracao = bundle?.getString("duracao")!!
        nomeAnime = bundle?.getString("nomeAnime")!!
        sinopse = bundle?.getString("sinopse")!!
        minutoAssistido = bundle?.getLong("minutoAssistido")

        val btLegenda = exoPlayer_episodio.findViewById<ImageView>(R.id.btSubtitle)
        val btProxEp = exoPlayer_episodio.findViewById<ImageView>(R.id.btProximoEp)
        val btVoltar = exoPlayer_episodio.findViewById<ImageView>(R.id.btVoltar)
        val trackSelector = DefaultTrackSelector(this)

        verificarTipo(btProxEp)

        btLegenda.setOnClickListener {
            subtitle = if (subtitle) {
                ativarLegenda(trackSelector)
                false
            } else {
                desativarLegenda(trackSelector)
                true
            }
        }

        btVoltar.setOnClickListener {
            onBackPressed()
        }

        btProxEp.setOnClickListener {
           episodiosDao.carregarProximoEp(codigoAnime, tituloEp, tipo!!, nomeAnime!!, imagem!!, this)
        }

        btProxEp.setOnLongClickListener {
            Toast.makeText(this, "Próximo Episódio", Toast.LENGTH_SHORT).show()
            true
        }

        carregarEpisodio(link!!, trackSelector, tipo!!, codigoAnime!!, tituloEp, imagem!!, nomeAnime!!)
    }

    private fun verificarTipo(btProxEp: ImageView) {
        if (tipo == "Filme") {
            btProxEp.visibility = GONE
        } else if (tipo == "Anime") {
            btProxEp.visibility = VISIBLE
        }
    }

    private fun carregarEpisodio(link: String,
                                 trackSelector: DefaultTrackSelector,
                                 tipo: String,
                                 codigoAnime: String,
                                 tituloEp: String,
                                 imagemAnime: String,
                                 nomeAnime: String, ){

        exoPlayer_episodio.findViewById<TextView>(R.id.tvNomeAnimeExo).text = tituloEp
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(link))
        simpleExoPlayer = SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).setSeekBackIncrementMs(10000).setSeekBackIncrementMs(10000).build()
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd().setPreferredTextLanguage("pt-br"))

        prepararPlayer(mediaSource, codigoAnime, tituloEp, tipo, nomeAnime, imagemAnime, minutoAssistido, trackSelector)
    }

    private fun prepararPlayer(
        mediaSource: ProgressiveMediaSource,
        codigoAnime: String,
        tituloEp: String,
        tipo: String,
        nomeAnime: String,
        imagem: String,
        minutoAssistido: Long, 
        trackSelector: DefaultTrackSelector
    ) {
        exoPlayer_episodio.player = simpleExoPlayer
        val user = Firebase.auth.currentUser
        simpleExoPlayer!!.setMediaSource(mediaSource, minutoAssistido)
        if(minutoAssistido <1 || user == null){
            simpleExoPlayer!!.setMediaSource(mediaSource, minutoAssistido)
        }
        else if(user != null || minutoAssistido.equals(0)){
            checarMinutoAssistido(mediaSource)
        }
        simpleExoPlayer!!.prepare()
        simpleExoPlayer!!.volume = 0.6f
        simpleExoPlayer!!.playWhenReady = true
        simpleExoPlayer!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_ENDED -> {
                        val user = auth.currentUser
                        if(user != null){
                            adicionarNoHistorico("Finalizado")
                        }
                        carregarProximoEp(codigoAnime, tituloEp, tipo, nomeAnime, imagem)
                    }
                    Player.STATE_READY ->{
                        checarLegenda(trackSelector)
                    }
                }
            }
            override fun onPlayerError(error: PlaybackException) {
                if (error != null) {
                    Toast.makeText(this@AssistirEpActivity, "Erro ao iniciar o vídeo", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        })
    }

    private fun checarLegenda(trackSelector: DefaultTrackSelector){
        val trackInfo = trackSelector.currentMappedTrackInfo
        val trackGroups = trackInfo!!.getTrackGroups(2)
        if(trackGroups.isEmpty){
            btSubtitle.setImageDrawable(getDrawable(R.drawable.ic_legenda_inativa))
            btSubtitle.isEnabled = false
        }
        else{
            return
        }
    }

    private fun desativarLegenda(trackSelector: DefaultTrackSelector){
        trackSelector.setParameters(
            trackSelector.buildUponParameters().setRendererDisabled(2, true))
        btSubtitle.setImageDrawable(resources.getDrawable(R.drawable.ic_legenda_inativa))
    }

    private fun ativarLegenda(trackSelector: DefaultTrackSelector) {
        trackSelector.setParameters(
            trackSelector.buildUponParameters().setRendererDisabled(2, false))
        btSubtitle.setImageDrawable(resources.getDrawable(R.drawable.ic_legenda_ativa))
    }

    private fun carregarProximoEp(codigoAnime: String, tituloEp: String, tipo: String, nomeAnime: String, imagem: String){
        episodiosDao.carregarProximoEp(codigoAnime, tituloEp, tipo, nomeAnime, imagem,  this)
    }

    private fun adicionarNoHistorico(status: String){
        val historico = ModelHistorico(
            codigoEp = codigoEp,
            codigoAnime = codigoAnime,
            data = com.google.firebase.Timestamp.now(),
            duracao = getDuracao(),
            imagem = imagem,
            link = link,
            minutoAssistido = simpleExoPlayer!!.currentPosition,
            nomeAnime = nomeAnime,
            saga = saga,
            sinopseEp = sinopse,
            tituloEp = tituloEp,
            tipo = tipo,
            status = status)
            usuarioDao.inserirNoHistorico(historico)
    }

    private fun getDuracao() : String{
        return duracao
    }

    private fun checarMinutoAssistido(mediaSource: ProgressiveMediaSource){
        db.collection("usuarios").document(auth.currentUser?.email!!).collection("historico")
            .whereEqualTo("tituloEp", tituloEp).get().addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for(document in task.result){
                        minutoAssistido = document["minutoAssistido"] as Long
                    }
                    if(minutoAssistido >1){
                        simpleExoPlayer!!.setMediaSource(mediaSource, minutoAssistido)
                    }
                    else{
                        simpleExoPlayer!!.setMediaSource(mediaSource)
                    }
                }
            }
        }

    override fun onPause() {
        super.onPause()
        val user = Firebase.auth.currentUser
        simpleExoPlayer!!.playWhenReady = false
        simpleExoPlayer!!.playbackState
        if(user != null && simpleExoPlayer!!.currentPosition >= simpleExoPlayer!!.duration){
            return
        }
        else if(user != null) {
            adicionarNoHistorico("Incompleto")
        }
    }

    override fun onRestart() {
        super.onRestart()
        simpleExoPlayer!!.playWhenReady = true
        simpleExoPlayer!!.playbackState
    }

    override fun onBackPressed() {
        episodiosDao.chamarTelaDetalhesAnimes(codigoAnime, this)
    }
}