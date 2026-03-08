package com.ssbprep.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssbprep.adapters.KnowledgeCategory
import com.ssbprep.adapters.KnowledgeExpandableAdapter
import com.ssbprep.adapters.KnowledgeSubItem
import com.ssbprep.databinding.ActivityServiceDetailBinding

class ServiceDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityServiceDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceType = intent.getStringExtra("SERVICE_TYPE") ?: "Army"
        setupUI(serviceType)

        binding.backButton.setOnClickListener { finish() }

        val categories = getCategoriesForService(serviceType)
        binding.detailRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.detailRecyclerView.adapter = KnowledgeExpandableAdapter(categories)
    }

    private fun setupUI(service: String) {
        binding.titleText.text = when (service) {
            "Army" -> "Indian Army"
            "AirForce" -> "Indian Air Force"
            "Navy" -> "Indian Navy"
            "Missiles" -> "Missile Systems"
            "Awards" -> "Honours & Awards"
            else -> "Information"
        }
    }

    private fun getCategoriesForService(service: String): List<KnowledgeCategory> {
        return when (service) {
            "Army" -> listOf(
                KnowledgeCategory(
                    "1. Fundamentals and Identity",
                    "The core essence of the Indian Army.",
                    subItems = listOf(
                        KnowledgeSubItem("Motto", "Seva Paramo Dharma (Service Before Self)."),
                        KnowledgeSubItem("Flag and Crest", "Symbolism of the crossed swords and the Lion Capital."),
                        KnowledgeSubItem("Ethos and Values", "The Chetwode Motto, Spirit of Sacrifice, and Secularism."),
                        KnowledgeSubItem("Role of the Army", "Primary: External threats. Secondary: Internal security/Disaster relief.")
                    )
                ),
                KnowledgeCategory(
                    "2. History, Traditions, and Lineage",
                    "Evolution and glorious past.",
                    subItems = listOf(
                        KnowledgeSubItem("Evolution", "Pre-1947 Presidency Armies to the present modern force."),
                        KnowledgeSubItem("Post-Independence Conflicts", "1947-48 (Kashmir), 1962 (China), 1965 & 1971 (Pakistan), 1999 (Kargil)."),
                        KnowledgeSubItem("Regimental System", "History of 'Martial Race' theory and transition to 'All India All Class'."),
                        KnowledgeSubItem("War Memorials", "National War Memorial (Delhi) and India Gate.")
                    )
                ),
                KnowledgeCategory(
                    "3. Organizational Architecture",
                    "The structure and command chain.",
                    subItems = listOf(
                        KnowledgeSubItem("Army Headquarters", "Integrated HQ of MoD, headed by COAS, VCOAS, and PSOs."),
                        KnowledgeSubItem("Order of Battle (ORBAT)", "Section → Platoon → Company → Battalion → Brigade → Division → Corps → Command.")
                    )
                ),
                KnowledgeCategory(
                    "4. Ranks, Insignia, and Protocol",
                    "Select a category to view the career progression flow.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Lieutenant", "Captain", "Major", "Lt Colonel", "Colonel", "Brigadier", "Major General", "Lt General", "General", "Field Marshal"),
                        "JCOs/NCOs/ORs" to listOf("Sepoy", "Lance Naik", "Naik", "Havildar", "Naib Subedar", "Subedar", "Subedar Major")
                    )
                ),
                KnowledgeCategory(
                    "5. Arms and Services",
                    "The working parts of the Army.",
                    subItems = listOf(
                        KnowledgeSubItem("Combat Arms", "Infantry, Armoured Corps, Mechanized Infantry."),
                        KnowledgeSubItem("Support Arms", "Artillery (Gunners), Engineers (Sappers), Signals (Mercury), AAD, Army Aviation."),
                        KnowledgeSubItem("Services", "ASC, AMC, AOC, EME, AEC, RVC, MNS, ADC."),
                        KnowledgeSubItem("Special Forces", "Para (SF) - Specialized roles in unconventional warfare.")
                    )
                ),
                KnowledgeCategory(
                    "6. Weapons and Technology",
                    "The iron fist of the Indian Army.",
                    subItems = listOf(
                        KnowledgeSubItem("Small Arms", "Pistols, SIG 716, AK-203, Sako TRG-42 sniper."),
                        KnowledgeSubItem("Artillery", "155mm Dhanush, M777 Ultra Light Howitzer, K9 Vajra, Pinaka Rocket System."),
                        KnowledgeSubItem("Armoured Vehicles", "T-90 Bhishma, T-72 Ajeya, Arjun MBT, BMP-2 Sarath."),
                        KnowledgeSubItem("Missiles", "Prithvi, Agni series, BrahMos supersonic cruise missile.")
                    )
                ),
                KnowledgeCategory(
                    "7. Training and Entry Schemes",
                    "How to join the elite force.",
                    subItems = listOf(
                        KnowledgeSubItem("Entry Schemes", "NDA, IMA (DE/UES/TGC), OTA (SSC-Non Tech/Tech/NCC Special)."),
                        KnowledgeSubItem("Training Academies", "NDA (Khadakwasla), IMA (Dehradun), OTA (Chennai)."),
                        KnowledgeSubItem("Specialized Schools", "HAWS (Gulmarg), CIJW (Vairengte), School of Artillery (Deolali).")
                    )
                ),
                KnowledgeCategory(
                    "8. SSB Centers (Army)",
                    "Selection Centers across India.",
                    subItems = listOf(
                        KnowledgeSubItem("Selection Center East (Allahabad)", "11 SSB, 14 SSB, 18 SSB, 19 SSB, 34 SSB. Known for its 'rejection' center myth but is actually highly professional.", "https://www.google.com/maps/search/Selection+Center+East+Allahabad"),
                        KnowledgeSubItem("Selection Center Central (Bhopal)", "20 SSB, 21 SSB, 22 SSB. Located in Sultania Infantry Lines.", "https://www.google.com/maps/search/Selection+Center+Central+Bhopal"),
                        KnowledgeSubItem("Selection Center South (Bangalore)", "17 SSB, 24 SSB. Located at Cubbon Road.", "https://www.google.com/maps/search/Selection+Center+South+Bangalore"),
                        KnowledgeSubItem("Selection Center North (Kapurthala)", "31 SSB, 32 SSB. Located in the heritage city of Kapurthala, Punjab.", "https://www.google.com/maps/search/Selection+Center+North+Kapurthala")
                    )
                ),
                KnowledgeCategory(
                    "9. Strategic Environment",
                    "Modern challenges and jointness.",
                    subItems = listOf(
                        KnowledgeSubItem("Border Management", "LOC (Pakistan), LAC (China), AGPL (Siachen)."),
                        KnowledgeSubItem("Jointness", "Chief of Defence Staff (CDS) and Integrated Theatre Commands (ITC)."),
                        KnowledgeSubItem("Modernization", "F-INSAS, Drone swarms, Night Vision, AI integration.")
                    )
                )
            )
            "AirForce" -> listOf(
                KnowledgeCategory(
                    "1. The Core Fleet (Detailed Aircraft Profiles)",
                    "Strategic and tactical air assets.",
                    subItems = listOf(
                        KnowledgeSubItem("Dassault Rafale", "4.5 generation powerhouse. Origin: France. Speed: Mach 1.8. Weapons: Meteor, SCALP, MICA."),
                        KnowledgeSubItem("Sukhoi Su-30MKI", "Backbone of IAF. Origin: Russia/India. Speed: Mach 2.0. Multirole air superiority fighter."),
                        KnowledgeSubItem("HAL Tejas", "Indigenous Light Combat Aircraft. Mk 1 and Mk 1A versions in service."),
                        KnowledgeSubItem("C-17 Globemaster III", "Strategic heavy lifter for rapid troop and equipment deployment."),
                        KnowledgeSubItem("HAL Prachand (LCH)", "Dedicated attack helicopter for high-altitude operations (up to 5000m).")
                    )
                ),
                KnowledgeCategory(
                    "2. IAF Commands",
                    "Operational and functional commands. Select a command to see HQ and strategic importance.",
                    selectableItems = mapOf(
                        "Western Command" to "HQ: New Delhi. Importance: Defends the sensitive borders with Pakistan and China in the north. Most critical operational command.",
                        "Eastern Command" to "HQ: Shillong. Importance: Responsible for the LAC in the eastern sector, including Sikkim and Arunachal Pradesh.",
                        "Central Command" to "HQ: Prayagraj. Importance: Responsible for the central sector and air defense of the heartland.",
                        "South Western Command" to "HQ: Gandhinagar. Importance: Guards the borders in Rajasthan and Gujarat.",
                        "Southern Command" to "HQ: Thiruvananthapuram. Importance: Focuses on maritime air operations and the Indian Ocean region.",
                        "Training Command" to "HQ: Bengaluru. Importance: Responsible for training all personnel of the IAF.",
                        "Maintenance Command" to "HQ: Nagpur. Importance: Handles the technical upkeep and logistics of the entire fleet."
                    )
                ),
                KnowledgeCategory(
                    "3. Ranks and Insignia",
                    "Select a category to view the career progression flow.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Flying Officer", "Flight Lieutenant", "Squadron Leader", "Wing Commander", "Group Captain", "Air Commodore", "Air Vice Marshal", "Air Marshal", "Air Chief Marshal", "Marshal of the Air Force"),
                        "Airmen" to listOf("Aircraftman", "Leading Aircraftman", "Corporal", "Sergeant", "Junior Warrant Officer", "Warrant Officer", "Master Warrant Officer")
                    )
                ),
                KnowledgeCategory(
                    "4. SSB Centers (AFSB)",
                    "Air Force Selection Boards.",
                    subItems = listOf(
                        KnowledgeSubItem("1 AFSB Dehradun", "Located at Clement Town. One of the oldest boards for pilot selection.", "https://www.google.com/maps/search/1+AFSB+Dehradun"),
                        KnowledgeSubItem("2 AFSB Mysore", "Located at CV Complex, Sidharth Nagar. Famous for CPSS testing.", "https://www.google.com/maps/search/2+AFSB+Mysore"),
                        KnowledgeSubItem("3 AFSB Gandhinagar", "Located at Sector 9. Serves candidates from western India.", "https://www.google.com/maps/search/3+AFSB+Gandhinagar"),
                        KnowledgeSubItem("4 AFSB Varanasi", "Located at Varanasi Cantt. Known for its historical significance.", "https://www.google.com/maps/search/4+AFSB+Varanasi"),
                        KnowledgeSubItem("5 AFSB Guwahati", "Located at VIP Road, Borjhar. Serves the North-East region.", "https://www.google.com/maps/search/5+AFSB+Guwahati")
                    )
                ),
                KnowledgeCategory(
                    "5. Weapons and Missile Systems",
                    "The sting of the Air Force.",
                    subItems = listOf(
                        KnowledgeSubItem("Air-to-Air", "Astra (Indigenous BVR), Meteor, MICA, R-77."),
                        KnowledgeSubItem("Air Defence", "S-400 Triumf, Akash, MR-SAM, Project Kusha.")
                    )
                )
            )
            "Navy" -> listOf(
                KnowledgeCategory(
                    "1. Identity and Heritage",
                    "Motto: Sham No Varunah (May the Lord of the Water be auspicious unto us).",
                    subItems = listOf(
                        KnowledgeSubItem("Father of Navy", "Chhatrapati Shivaji Maharaj - for his vision of coastal forts."),
                        KnowledgeSubItem("New Ensign", "The octagonal seal inspired by Shivaji's Royal Seal."),
                        KnowledgeSubItem("Navy Day", "4th December (Operation Trident - 1971 attack on Karachi).")
                    )
                ),
                KnowledgeCategory(
                    "2. Naval Commands",
                    "Strategic commands of the Indian Navy.",
                    selectableItems = mapOf(
                        "Western Command" to "HQ: Mumbai. Known as the 'Sword Arm' of the Navy. Directs operations in the Arabian Sea.",
                        "Eastern Command" to "HQ: Visakhapatnam. Known as the 'Sunrise Command'. Focuses on the Bay of Bengal.",
                        "Southern Command" to "HQ: Kochi. The Training Command. Responsible for all naval training.",
                        "ANC Command" to "HQ: Port Blair. India's only Tri-Service Command, guarding the Malacca Straits."
                    )
                ),
                KnowledgeCategory(
                    "3. Rank Structure",
                    "Select a category to view the career progression flow.",
                    rankGroups = mapOf(
                        "Officers" to listOf("Sub Lieutenant", "Lieutenant", "Lt Commander", "Commander", "Captain", "Commodore", "Rear Admiral", "Vice Admiral", "Admiral", "Admiral of the Fleet"),
                        "Sailors" to listOf("Seaman II", "Seaman I", "Leading Seaman", "Petty Officer", "Chief Petty Officer", "MCPO II", "MCPO I")
                    )
                ),
                KnowledgeCategory(
                    "4. SSB Centers (NSB)",
                    "Naval Selection Boards.",
                    subItems = listOf(
                        KnowledgeSubItem("NSB Coimbatore", "Located at INS Agrani. Primary board for naval entries.", "https://www.google.com/maps/search/NSB+Coimbatore"),
                        KnowledgeSubItem("NSB Visakhapatnam", "Located at the Eastern Naval Command.", "https://www.google.com/maps/search/NSB+Visakhapatnam"),
                        KnowledgeSubItem("SCC Kolkata", "Selection Center Central for naval candidates in the east.", "https://www.google.com/maps/search/SCC+Kolkata"),
                        KnowledgeSubItem("12 SSB Bangalore", "Located at Cubbon Road. Shared board for naval entries.", "https://www.google.com/maps/search/12+SSB+Bangalore")
                    )
                ),
                KnowledgeCategory(
                    "5. The Fleet & Submarines",
                    "Surface and silent combatants.",
                    subItems = listOf(
                        KnowledgeSubItem("Aircraft Carriers", "INS Vikrant (IAC-1) and INS Vikramaditya."),
                        KnowledgeSubItem("Destroyers", "Visakhapatnam-class (Project 15B) stealth destroyers."),
                        KnowledgeSubItem("Silent Arm", "Kalvari-class (Scorpene) and Arihant-class (Nuclear SSBN)."),
                        KnowledgeSubItem("Naval Aviation", "P-8I Poseidon, MiG-29K, and MH-60R Seahawk.")
                    )
                ),
                KnowledgeCategory(
                    "6. Elite Forces & Weapons",
                    "MARCOS and Naval Weaponry.",
                    subItems = listOf(
                        KnowledgeSubItem("MARCOS", "The 'Bearded Crocodiles' - specialized in amphibious warfare."),
                        KnowledgeSubItem("Weaponry", "BrahMos cruise missile, Barak-8 SAM, Varunastra torpedo.")
                    )
                ),
                KnowledgeCategory(
                    "7. Strategic Concepts",
                    "Security and Growth for All in the Region (SAGAR).",
                    subItems = listOf(
                        KnowledgeSubItem("Blue Water Navy", "Capability to operate globally in deep ocean waters."),
                        KnowledgeSubItem("Mission-Based", "Constant presence at choke points like Strait of Malacca.")
                    )
                )
            )
            else -> emptyList()
        }
    }
}
