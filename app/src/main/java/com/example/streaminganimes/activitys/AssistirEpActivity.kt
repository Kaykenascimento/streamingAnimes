package com.example.streaminganimes.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.streaminganimes.R
import com.example.streaminganimes.dao.EpisodiosDao
import com.example.streaminganimes.dao.UsuarioDao
import com.example.streaminganimes.extensions.formatarData
import com.example.streaminganimes.models.ModelHistorico
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import kotlinx.android.synthetic.main.activity_assistir_ep.*
import kotlinx.android.synthetic.main.custom_controller_videoview.*
import java.util.*


class AssistirEpActivity : AppCompatActivity() {

    private val episodiosDao = EpisodiosDao()
    private val usuarioDao = UsuarioDao()
    private var subtitle: Boolean = false
    private var simpleExoPlayer: SimpleExoPlayer? = null
    private var codigoAnime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assistir_ep)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val bundle: Bundle? = this.intent.extras
        codigoAnime = bundle?.getString("codigoAnime")!!
        val tituloEp = bundle?.getString("titulo")!!
        val link = bundle?.getString("link")
        val codigoEp = bundle?.getString("codigoEp")!!
        val tipo = bundle?.getString("tipo")
        val imagemAnime = bundle?.getString("imagemAnime")
        val saga = bundle?.getString("saga")
        val duracao = bundle?.getString("duracao")
        val nomeAnime = bundle?.getString("nomeAnime")
        val sinopse = bundle?.getString("sinopse")

        val btLegenda = exoPlayer_episodio.findViewById<ImageView>(R.id.btSubtitle)
        val btProxEp = exoPlayer_episodio.findViewById<ImageView>(R.id.btProximoEp)
        val trackSelector = DefaultTrackSelector(this)

        btLegenda.setOnClickListener {
            subtitle = if (subtitle) {
                ativarLegenda(trackSelector)
                false
            } else {
                desativarLegenda(trackSelector)
                true
            }
        }

        btProxEp.setOnClickListener {
           episodiosDao.carregarProximoEp(codigoAnime, tituloEp, tipo!!, nomeAnime!!, imagemAnime!!, this)
        }

        btProxEp.setOnLongClickListener {
            Toast.makeText(this, "Próximo Episódio", Toast.LENGTH_SHORT).show()
            true
        }

        carregarEpisodio(link!!, trackSelector, tipo!!, codigoAnime!!, tituloEp, codigoEp, duracao!!, imagemAnime!!, nomeAnime!!, saga!!, sinopse!!)
    }

    private fun carregarEpisodio(link: String,
                                 trackSelector: DefaultTrackSelector,
                                 tipo: String,
                                 codigoAnime: String,
                                 tituloEp: String,
                                 codigoEp: String,
                                 duracao: String,
                                 imagemAnime: String,
                                 nomeAnime: String,
                                 saga: String,
                                 sinopse: String){
        if(tipo == "Filme"){
            exoPlayer_episodio.findViewById<ImageView>(R.id.btProximoEp).visibility = GONE
        }
        exoPlayer_episodio.findViewById<TextView>(R.id.tvNomeAnimeExo).text = tituloEp
        val dataSourceFactory = DefaultHttpDataSource.Factory()
        val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(link))
        simpleExoPlayer = SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).setSeekBackIncrementMs(10000).setSeekBackIncrementMs(10000).build()
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd().setPreferredTextLanguage("pt-br"))
        exoPlayer_episodio.player = simpleExoPlayer
        simpleExoPlayer!!.setMediaSource(mediaSource)
        simpleExoPlayer!!.prepare()
        simpleExoPlayer!!.volume = 0.6f
        simpleExoPlayer!!.playWhenReady = true
        simpleExoPlayer!!.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState){
                    Player.STATE_ENDED -> carregarProximoEp(codigoAnime, tituloEp, tipo, nomeAnime, imagemAnime)
                }
            }
        })
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

    private fun carregarProximoEp(codigoAnime: String, tituloEp: String, tipo: String, nomeAnime: String, imagemAnime: String){
        episodiosDao.carregarProximoEp(codigoAnime, tituloEp, tipo, nomeAnime, imagemAnime,  this)
    }

    private fun adicionarNoHistorico(historico: ModelHistorico){
        usuarioDao.inserirNoHistorico(historico)
    }

    override fun onPause() {
        super.onPause()
        simpleExoPlayer?.playWhenReady = false
        simpleExoPlayer?.playbackState
    }

    override fun onRestart() {
        super.onRestart()
        simpleExoPlayer?.playWhenReady = true
        simpleExoPlayer?.playbackState
    }

    override fun onBackPressed() {
        episodiosDao.chamarTelaDetalhesAnimes(codigoAnime, this)
    }
}