// SPDX-FileCopyrightText: 2020-2024 Robin Lindén <dev@robinlinden.eu>
//
// SPDX-License-Identifier: GPL-3.0-only

package ltd.evilcorp.domain.tox

import im.tox.tox4j.core.enums.ToxConnection
import im.tox.tox4j.core.enums.ToxFileKind
import im.tox.tox4j.core.enums.ToxMessageType
import im.tox.tox4j.core.enums.ToxUserStatus
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import ltd.evilcorp.core.vo.ConnectionStatus
import ltd.evilcorp.core.vo.FileKind
import ltd.evilcorp.core.vo.MessageType
import ltd.evilcorp.core.vo.UserStatus

private fun byteArrayOf(vararg bytes: Int) = ByteArray(bytes.size) { bytes[it].toByte() }

class ToxUtilTest {
    @Test
    fun `connection enums can be converted`() {
        assert(ToxConnection.entries.size == ConnectionStatus.entries.size)
        assert(ConnectionStatus.entries.size == 3)

        ToxConnection.entries.forEach {
            assertEquals(it.ordinal, it.toConnectionStatus().ordinal)
        }

        assertEquals(ToxConnection.NONE.ordinal, ConnectionStatus.None.ordinal)
        assertEquals(ToxConnection.UDP.ordinal, ConnectionStatus.UDP.ordinal)
        assertEquals(ToxConnection.TCP.ordinal, ConnectionStatus.TCP.ordinal)
    }

    @Test
    fun `message type enums can be converted`() {
        assertEquals(2, ToxMessageType.entries.size)

        ToxMessageType.entries.forEach { type ->
            assertEquals(type.ordinal, type.toMessageType().ordinal)
            assertEquals(type, type.toMessageType().toToxType())
        }

        assertEquals(ToxMessageType.NORMAL.ordinal, MessageType.Normal.ordinal)
        assertEquals(ToxMessageType.ACTION.ordinal, MessageType.Action.ordinal)
    }

    @Test
    fun `status enums can be converted`() {
        assert(ToxUserStatus.entries.size == UserStatus.entries.size)
        assert(UserStatus.entries.size == 3)

        ToxUserStatus.entries.forEach {
            assertEquals(it.ordinal, it.toUserStatus().ordinal)
        }

        UserStatus.entries.forEach { type ->
            assertEquals(type, type.toToxType().toUserStatus())
        }

        assertEquals(ToxUserStatus.NONE.ordinal, UserStatus.None.ordinal)
        assertEquals(ToxUserStatus.AWAY.ordinal, UserStatus.Away.ordinal)
        assertEquals(ToxUserStatus.BUSY.ordinal, UserStatus.Busy.ordinal)
    }

    @Test
    fun `file kind can be converted`() {
        assertEquals(ToxFileKind.AVATAR, FileKind.Avatar.ordinal)
        assertEquals(ToxFileKind.AVATAR, FileKind.Avatar.toToxtype())
        assertEquals(ToxFileKind.DATA, FileKind.Data.ordinal)
        assertEquals(ToxFileKind.DATA, FileKind.Data.toToxtype())
    }

    @Test
    fun `public keys can be converted`() {
        val keyString = "76518406F6A9F2217E8DC487CC783C25CC16A15EB36FF32E335A235342C48A39"
        assert(keyString.hexToBytes().size == 32)
        assertEquals(keyString, keyString.hexToBytes().bytesToHex().uppercase())

        val keyBytes = byteArrayOf(
            0x76, 0x51, 0x84, 0x06, 0xF6, 0xA9, 0xF2, 0x21, 0x7E, 0x8D, 0xC4, 0x87, 0xCC, 0x78, 0x3C, 0x25,
            0xCC, 0x16, 0xA1, 0x5E, 0xB3, 0x6F, 0xF3, 0x2E, 0x33, 0x5A, 0x23, 0x53, 0x42, 0xC4, 0x8A, 0x39,
        )

        assertEquals(keyBytes.size, keyString.hexToBytes().size)
        assert(keyBytes.contentEquals(keyString.hexToBytes()))

        val anotherKeyString = "7B6704162C6532A5A8F0840A3680672D0E9D3E62B6419FFD88D9880669482169"
        assertEquals(anotherKeyString, anotherKeyString.hexToBytes().bytesToHex().uppercase())
        assertNotEquals(anotherKeyString.hexToBytes(), keyString.hexToBytes())
    }

    @Test
    fun `casing of public keys does not matter`() {
        val keyString = "76518406F6A9F2217E8DC487CC783C25CC16A15EB36FF32E335A235342C48A39"
        assertContentEquals(keyString.uppercase().hexToBytes(), keyString.lowercase().hexToBytes())
    }
}
