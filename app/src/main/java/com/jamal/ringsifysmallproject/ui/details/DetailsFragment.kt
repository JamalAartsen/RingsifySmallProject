package com.jamal.ringsifysmallproject.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jamal.ringsifysmallproject.R
import com.jamal.ringsifysmallproject.databinding.FragmentDetailsCharacterBinding

class DetailsFragment : Fragment(R.layout.fragment_details_character) {

    private var _binding: FragmentDetailsCharacterBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.apply {
//            val character = args.character
//
//            detailsName.text = character.name
//            characterRace.text = character.race
//            characterBirth.text = character.birth
//            characterDeath.text = character.death
//
//            var uri: Uri? = null
//            uri = if (uri != null) {
//                Uri.parse(character.wikiUrl)
//            } else {
//                Uri.parse("https://lotr.fandom.com/")
//            }
//                       val intent = Intent(Intent.ACTION_VIEW, uri)
//            fandomLinkButton.setOnClickListener {
//                context?.startActivity(intent)
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}